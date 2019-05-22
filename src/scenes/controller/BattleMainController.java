package scenes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import main.Main;
import main.pokemon.Pokemon;
import main.pokemon.PokemonMapper;
import main.scenemanager.SceneManager;
import main.users.Trainer;

import java.util.ArrayList;

public class BattleMainController implements Controller {

    @FXML
    Button btnFight, btnFlee, btnSwitch, btnHelp;
    @FXML
    Pane paneHelp;
    private Main main;
    private Pokemon[] userTeam = new Pokemon[6], enemyTeam = new Pokemon[6];
    private ArrayList<String> battleMessages = new ArrayList<>();
    private Boolean userTurn = true;
    private int pokemonRemaining = 6, enemyRemaining = 6;
    private boolean fighting = false;
    private double enemyHp, userHP;
    private Boolean help = false;
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
    // These are used for the Battle System
    // They are stored in this order: HP, Speed, Attack, Defense
    private ArrayList<Integer> userStats = new ArrayList<>();
    private ArrayList<Integer> enemyStats = new ArrayList<>();
    private String enemyType, userType;

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
        Pokemon[] allPokemons = main.getAllPokemon().toArray(new Pokemon[main.getAllPokemon().size()]);

        for (int maxSix = 0; maxSix < mappedTeam.size(); maxSix++) {
            userTeam[maxSix] = main.getPokemonById(mappedTeam.get(maxSix).getId());
            pokemonRemaining = maxSix + 1;
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

        lblPokemonHP.setText(userStats.get(0).toString());
        lblEnemyHP.setText(enemyStats.get(0).toString());

        getAttackingFirst();

        fighting = false;
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

        fighting = false;

        teamBall1.setVisible(true);
        teamBall2.setVisible(true);
        teamBall3.setVisible(true);
        teamBall4.setVisible(true);
        teamBall5.setVisible(true);
        teamBall6.setVisible(true);

        enemyBall1.setVisible(true);
        enemyBall2.setVisible(true);
        enemyBall3.setVisible(true);
        enemyBall4.setVisible(true);
        enemyBall5.setVisible(true);
        enemyBall6.setVisible(true);
    }

    public void backToTrainerMenu() {
        main.requestSceneChange(SceneManager.sceneName.TRAINERMENU);
    }

    private void getAttackingFirst() {
        // Compares Speeds to see who is going to attack first
        if (userStats.get(1) >= enemyStats.get(1)) {
            userTurn = true;
        } else {
            userTurn = false;
        }
    }

    private void updateMessageBoard() {
        ObservableList<String> readableMessages = FXCollections.observableArrayList(battleMessages);

        messageBoard.setItems(readableMessages);
    }

    private void updateProgressBar(Double damage) {
        // A full ProgressBar is value 1 so the damage needs to be less than 1
        damage = damage / 100;

        if (userTurn) {
            hpEnemy.setProgress(hpEnemy.getProgress() - damage);
        } else {
            hpPokemon.setProgress(hpPokemon.getProgress() - damage);
        }
    }

    private void updateLabels() {
        lblPokemon.setText(userTeam[0].getName());
        lblPokemonHP.setText(Double.toString(userHP));

        lblEnemy.setText(enemyTeam[0].getName());
        lblEnemyHP.setText(Double.toString(enemyHp));
    }

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
        double damage = 0.0;

        if (!fighting) {
            enemyHp = enemyStats.get(0);
            userHP = userStats.get(0);
        }

        if (userTurn) {
            // sets the active pokemon to fighting needed for the labels!
            fighting = true;
            // Ask Isak for future system
            // Damage = (Bonus * Attack) - Defense
            damage = (calculateTypeBonus() * (userStats.get(2) * (int) ((Math.random() * userStats.get(2)) + 1)) - (enemyStats.get(3) / 2.0));

            // HP = HP - Damage (Damage is rounded to the nearest integer)
            enemyHp = enemyHp - damage;

            // Sets a message that the pokemon attacked
            battleMessages.add(lblPokemon.getText() + " Attacked for: " + damage + " points of damage!");

            // If the enemy is below 0 then the pokemon is defeated
            if (enemyHp <= 0) {
                switch (enemyRemaining) {
                    case 1: {
                        enemyRemaining = 0;
                        enemyBall1.setVisible(false);
                        endBattle();
                        break;
                    }
                    case 2: {
                        enemyRemaining = 1;
                        enemyBall2.setVisible(false);
                        break;
                    }
                    case 3: {
                        enemyRemaining = 2;
                        enemyBall3.setVisible(false);
                        break;
                    }
                    case 4: {
                        enemyRemaining = 3;
                        enemyBall4.setVisible(false);
                        break;
                    }
                    case 5: {
                        enemyRemaining = 4;
                        enemyBall5.setVisible(false);
                        break;
                    }
                    case 6: {
                        enemyRemaining = 5;
                        enemyBall6.setVisible(false);
                        break;
                    }
                    default: {
                        System.out.println("More Pokemon were Defeated than Exist!");
                    }
                }
                pokemonDefeated(lblEnemy.getText());
            }


        } else {
            fighting = true;
            damage = (calculateTypeBonus() * (enemyStats.get(2) * (int) ((Math.random() * enemyStats.get(2)) + 1)) - (userStats.get(3) / 2.0));
            battleMessages.add(lblEnemy.getText() + " Attacked for: " + damage + " points of damage!");
            userHP = userHP - damage;
            if (userHP <= 0) {
                switch (pokemonRemaining) {
                    case 1: {
                        teamBall1.setVisible(false);
                        endBattle();
                        break;
                    }
                    case 2: {
                        teamBall2.setVisible(false);
                        break;
                    }
                    case 3: {
                        teamBall3.setVisible(false);
                        break;
                    }
                    case 4: {
                        teamBall4.setVisible(false);
                        break;
                    }
                    case 5: {
                        teamBall5.setVisible(false);
                        break;
                    }
                    case 6: {
                        teamBall6.setVisible(false);
                        break;
                    }
                    default: {
                        System.out.println("More Pokemon were Defeated than Exist!");
                    }
                }
                pokemonDefeated(lblPokemon.getText());
            }
        }

        updateMessageBoard();
        updateProgressBar(damage);
        updateLabels();

        if (userTurn) {
            userTurn = false;
            fightTurn();
        } else {
            userTurn = true;
        }
    }

    public void switchPokemon() {
        fighting = false;

        // The active Pokemon is the first one in the array
        if (userTurn) {
            for (int index = 0; index < pokemonRemaining; index++) {
                Pokemon temp = userTeam[index + 1];
                if (temp != null) {
                    userTeam[index + 1] = userTeam[index];
                    userTeam[index] = temp;
                } else {
                    battleMessages.add("You don't have any other available Pokemon");
                    updateMessageBoard();
                }
            }
        } else {
            for (int index = 0; index < enemyRemaining; index++) {
                Pokemon temp = enemyTeam[index + 1];
                enemyTeam[index + 1] = enemyTeam[index];
                enemyTeam[index] = temp;
            }
        }

        if (userTurn) {
            Image user = main.getPokemonImage(userTeam[0].getName());
            imgPokemon.setImage(user);

            lblPokemon.setText(userTeam[0].getName());
            lblPokemonHP.setText(Integer.toString(userTeam[0].getHealth()));

            userTurn = false;
            fightTurn();
        } else {
            Image enemy = main.getPokemonImage(enemyTeam[0].getName());
            imgEnemy.setImage(enemy);

            lblEnemy.setText(enemyTeam[0].getName());
            lblEnemyHP.setText(Integer.toString(enemyTeam[0].getHealth()));

            userTurn = true;
        }


    }

    private void getStats() {
        enemyStats.clear();
        userStats.clear();

        // Might Change Later
        Pokemon enemyPokemon = enemyTeam[0];
        Pokemon userPokemon = userTeam[0];

        // NOT DUPLICATE ONE IS ENEMY ONE IS POKEMON
        enemyStats.add(enemyPokemon.getHealth());
        enemyStats.add(enemyPokemon.getSpeed());
        enemyStats.add(enemyPokemon.getAttack());
        enemyStats.add(enemyPokemon.getDefense());

        enemyType = enemyPokemon.getType();

        userStats.add(userPokemon.getHealth());
        userStats.add(userPokemon.getSpeed());
        userStats.add(userPokemon.getAttack());
        userStats.add(userPokemon.getDefense());

        userType = userPokemon.getType();
    }

    private void pokemonDefeated(String name) {
        battleMessages.add(name + " has been knocked Unconscious!");
        updateMessageBoard();
        switchPokemon();
    }

    // WORK IN PROGRESS
    private Double calculateTypeBonus() {
        // This type is temp ( ͡° ͜ʖ ͡°)
        String tempType;

        if (!userTurn) {
            tempType = userType;
            userType = enemyType;
            enemyType = tempType;
        }

        // This part is the Water vs Other bonuses first weakness last
        if (userType.contains("Water") && enemyType.contains("Fire")) {
            return 2.0;
        } else if (userType.contains("Water") && enemyType.contains("Electric")) {
            return 2.0;
        } else if (userType.contains("Water") && enemyType.contains("Grass")) {
            return 0.5;
        }

        // This part is the Fire vs Other
        if (userType.contains("Fire") && enemyType.contains("Grass")) {
            return 2.0;
        } else if (userType.contains("Fire") && enemyType.contains("Water")) {
            return 0.5;
        } else if (userType.contains("Fire") && enemyType.contains("Fire")) {
            return 0.5;
        }

        // This part is the Grass vs Other
        if (userType.contains("Grass") && enemyType.contains("Water")) {
            return 2.0;
        } else if (userType.contains("Grass") && enemyType.contains("Fire")) {
            return 0.5;
        } else if (userType.contains("Grass") && enemyType.contains("Flying")) {
            return 0.5;
        }

        // This Part is the Electric vs Other
        if (userType.contains("Electric") && enemyType.contains("Water")) {
            return 2.0;
        } else if (userType.contains("Electric") && enemyType.contains("Flying")) {
            return 0.5;
        } else if (userType.contains("Electric") && enemyType.contains("Dragon")) {
            return 0.5;
        } else if (userType.contains("Electric") && enemyType.contains("Ground")) {
            return 0.0;
        }

        return 1.0;
    }

    private void endBattle() {
        btnFight.setVisible(false);
        btnFlee.setVisible(false);
        btnSwitch.setVisible(false);

        battleMessages.add("The battle is over you can stay and review what happened");
        battleMessages.add("When you're ready hit the back button");
        updateMessageBoard();
    }

    public void showHelp() {

        if(help == true){
            paneHelp.setVisible(false);
            help = false;
        }else{
            paneHelp.setVisible(true);
            help = true;
        }
    }
}
