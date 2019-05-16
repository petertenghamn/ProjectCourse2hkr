package scenes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import main.Main;
import main.pokemon.Pokemon;
import main.pokemon.PokemonMapper;
import main.scenemanager.SceneManager;
import main.users.Trainer;

import java.util.ArrayList;

public class BattleMainController implements Controller {

    @Override
    public void setMain(Main m) {
        main = m;
    }

    // The setUp MUST have:
    // Generation of enemy team so that it's random every time.
    @Override
    public void setUp() {
        // Need the pokemon to be in an array of Pokemon
        ArrayList<PokemonMapper> mappedTeam = ((Trainer) main.getCurrentUser()).getTeam();
        Pokemon[] allPokemons = main.getAllPokemon();

        for (int maxSix = 0; maxSix < mappedTeam.size(); maxSix++) {
            userTeam[maxSix] = main.getPokemonById(mappedTeam.get(maxSix).getId());
            pokemonRemaining = maxSix;
        }

        // Generation of the enemy team takes random pokemon from the main pokemon array
        for (int generate = 0; generate < 6; generate++) {
            // Change the numbers on the random to fit the numbers of pokemon
            enemyTeam[generate] = allPokemons[(int) (Math.random() * ((10 - 1) + 1)) + 1];
        }

        // The image setting up takes the first pokemon of each team
        Image pokemon = main.getPokemonImage(userTeam[0].getName());
        Image enemy = main.getPokemonImage(enemyTeam[0].getName());

        imgPokemon.setImage(pokemon);
        imgEnemy.setImage(enemy);

        lblPokemon.setText(userTeam[0].getName());
        lblEnemy.setText(enemyTeam[0].getName());

        getStats();

        lblPokemonHP.setText(userStats.get(1).toString());
        lblEnemyHP.setText(enemyStats.get(1).toString());

        getAttackingFirst();
    }

    @Override
    public void reset() {
        Image image = new Image("scenes/view/images/pokeLogo.png");
        imgPokemon.setImage(image);
        imgEnemy.setImage(image);

        lblPokemon.setText("HP :");
        lblEnemy.setText("HP :");

        hpPokemon.setProgress(1);
        hpEnemy.setProgress(1);

        battleMessages.clear();
        messageBoard.getItems().clear();

        pokemonRemaining = 6;
        enemyRemaining = 6;

        btnFight.setVisible(true);
        btnFlee.setVisible(true);
        btnSwitch.setVisible(true);
    }

    private Main main;
    private Pokemon[] userTeam = new Pokemon[6], enemyTeam = new Pokemon[6];
    private ArrayList<String> battleMessages = new ArrayList<>();
    private Boolean userTurn = true;
    private int pokemonRemaining = 6, enemyRemaining = 6;

    @FXML
    Button btnFight, btnFlee, btnSwitch;

    @FXML
    private ListView<String> messageBoard;

    @FXML
    private ImageView imgPokemon, imgEnemy;

    @FXML
    private Label lblPokemon, lblEnemy, lblPokemonHP, lblEnemyHP;

    @FXML
    private ProgressBar hpPokemon, hpEnemy;

    @FXML
    private Ellipse enemyBall1, enemyBall2, enemyBall3, enemyBall4, enemyBall5, enemyBall6;

    @FXML
    private Ellipse teamBall1, teamBall2, teamBall3, teamBall4, teamBall5, teamBall6;

    public void backToTrainerMenu() {
        main.requestSceneChange(SceneManager.sceneName.TRAINERMENU);
    }

    private void getAttackingFirst(){
        // Compares Speeds to see who is going to attack first
        if (userStats.get(1) >= enemyStats.get(1)){
            userTurn = true;
        }else {
            userTurn = false;
        }
    }

    private void updateMessageBoard() {
        ObservableList<String> readableMessages = FXCollections.observableArrayList(battleMessages);

        messageBoard.setItems(readableMessages);
    }

    private void updateProgressBar(Double damage) {
        if (userTurn) {
            hpEnemy.setProgress(hpEnemy.getProgress() - damage);
        } else {
            hpPokemon.setProgress(hpPokemon.getProgress() - damage);
        }
    }

    private void updateLabels() {

    }

    // requires the lbl of the pokemons to have been updated beforehand
    private void updateActivePokemon() {
        Image pokemon = main.getPokemonImage(lblPokemon.getText());
        Image enemy = main.getPokemonImage(lblEnemy.getText());
        imgPokemon.setImage(pokemon);
        imgEnemy.setImage(enemy);
    }

    // These are used for the Battle System
    // They are stored in this order: HP, Speed, Attack, Defense
    private ArrayList<Integer> userStats = new ArrayList<>();
    private ArrayList<Integer> enemyStats = new ArrayList<>();

    private String enemyType, userType;

    public void attemptEscape() {
        boolean escapeSuccess = true;

        double enemyHP = hpEnemy.getProgress();
        int escapeChance = 0;

        // Randomizes a number to see if the user can escape
        // The higher the enemyHP the less likely the user is allowed to escape
        // escapeChance = (int)(Math.random() * ((max-min)+1)+min;
        if (enemyHP == 1) {
            escapeChance = (int) (Math.random() * ((5 - 1) + 1)) + 1;

        } else if (enemyHP < 1 && enemyHP >= 0.75) {
            escapeChance = (int) (Math.random() * ((7 - 2) + 1)) + 2;

        } else if (enemyHP < 0.75 && enemyHP >= 0.5) {
            escapeChance = (int) (Math.random() * ((8 - 3) + 1)) + 3;

        } else if (enemyHP < 0.5 && enemyHP >= 0.25) {
            escapeChance = (int) (Math.random() * ((10 - 4) + 1)) + 4;

        } else {
            escapeChance = 10;
        }

        // If the chance to escape is higher than 5 then the escape is successful
        // Right now there are only two options escape or not
        // Later escapeChance could be used for more such as: Counting the escape as a loss
        if (escapeChance >= 5) {
            System.out.println("You escaped Successfully!");
        } else {
            escapeSuccess = false;
        }

        if (escapeSuccess) {
            main.requestSceneChange(SceneManager.sceneName.TRAINERMENU);
        } else {
            battleMessages.add(lblEnemy.getText() + " won't let you escape!");
            userTurn = false;
            updateMessageBoard();
            fightTurn();
        }
    }

    public void fightTurn() {
        getStats();

        double damage = 0.0;

        if (userTurn) {
            // Damage = (Bonus * Attack) - Defense
            damage = (calculateTypeBonus() * userStats.get(2)) - enemyStats.get(3);

            // HP = HP - Damage (Damage is rounded to the nearest integer)
            enemyStats.set(0, (enemyStats.get(0) - (int) Math.round(damage)));

            // If the enemy is below 0 then the pokemon is defeated
            if (enemyStats.get(0) <= 0){
                switch (enemyRemaining){
                    case 1:{
                        enemyBall1.setFill(Paint.valueOf("Black"));
                        endBattle();
                        break;
                    }
                    case 2:{
                        enemyBall2.setFill(Paint.valueOf("Black"));
                        break;
                    }
                    case 3:{
                        enemyBall3.setFill(Paint.valueOf("Black"));
                        break;
                    }
                    case 4:{
                        enemyBall4.setFill(Paint.valueOf("Black"));
                        break;
                    }
                    case 5:{
                        enemyBall5.setFill(Paint.valueOf("Black"));
                        break;
                    }
                    case 6:{
                        enemyBall6.setFill(Paint.valueOf("Black"));
                        break;
                    }
                    default:{
                        System.out.println("More Pokemon were Defeated than Exist!");
                    }
                }
                pokemonDefeated(lblEnemy.getText());
            }
        } else {
            damage = (calculateTypeBonus() * enemyStats.get(2)) - userStats.get(3);

            userStats.set(0, (userStats.get(0) - (int) Math.round(damage)));

            if (userStats.get(0) <= 0){
                switch (pokemonRemaining){
                    case 1:{
                        teamBall1.setFill(Paint.valueOf("Black"));
                        endBattle();
                        break;
                    }
                    case 2:{
                        teamBall2.setFill(Paint.valueOf("Black"));
                        break;
                    }
                    case 3:{
                        teamBall3.setFill(Paint.valueOf("Black"));
                        break;
                    }
                    case 4:{
                        teamBall4.setFill(Paint.valueOf("Black"));
                        break;
                    }
                    case 5:{
                        teamBall5.setFill(Paint.valueOf("Black"));
                        break;
                    }
                    case 6:{
                        teamBall6.setFill(Paint.valueOf("Black"));
                        break;
                    }
                    default:{
                        System.out.println("More Pokemon were Defeated than Exist!");
                    }
                }

                pokemonDefeated(lblPokemon.getText());
            }
        }

        updateProgressBar(damage);
    }

    private void getStats() {
        for (Pokemon enemyPokemon : enemyTeam) {
            if (enemyPokemon.getName().equalsIgnoreCase(lblEnemy.getText())) {
                enemyStats.add(enemyPokemon.getHealth());
                enemyStats.add(enemyPokemon.getSpeed());
                enemyStats.add(enemyPokemon.getAttack());
                enemyStats.add(enemyPokemon.getDefense());

                enemyType = enemyPokemon.getType();
            }
        }
        for (Pokemon userPokemon : userTeam) {
            if (userPokemon.getName().equalsIgnoreCase(lblPokemon.getText())) {
                userStats.add(userPokemon.getHealth());
                userStats.add(userPokemon.getSpeed());
                userStats.add(userPokemon.getAttack());
                userStats.add(userPokemon.getDefense());

                userType = userPokemon.getType();
            }
        }
    }

    private void pokemonDefeated(String name){
        battleMessages.add(name + " has been knocked Unconscious!");
        updateMessageBoard();
        switchPokemon();
    }

    // WORK IN PROGRESS
    private Double calculateTypeBonus(){
        return 1.0;
    }

    // Still needed
    public void switchPokemon() {

    }

    private void endBattle(){
        btnFight.setVisible(false);
        btnFlee.setVisible(false);
        btnSwitch.setVisible(false);
    }
}
