package scenes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Ellipse;
import main.Main;
import main.pokemon.Pokemon;
import main.pokemon.PokemonMapper;
import main.scenemanager.SceneManager;
import main.users.Trainer;
import main.users.User;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class BattleMainController implements Controller {

    @FXML
    Button btnFight, btnFlee, btnSwitch, btnHelp, btnBack;
    @FXML
    Pane paneHelp;
    private Main main;
    private Pokemon userPokemon, enemyPokemon;
    private ArrayList<Pokemon> userTeam, enemyTeam;
    private ArrayList<String> battleMessages = new ArrayList<>();
    private double enemyHp, userHP;
    private boolean help = false, escaping = false, battleEnded = false;
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

    // This is used for the Music and the Controller must implement Initializable

    @Override
    public void setMain(Main m) {
        main = m;
    }

    // The setUp MUST have:
    // Generation of enemy team so that it's random every time.
    @Override
    public void setUp() {
        // The Music and Sounds of the Battle Scene Start here

        //get pokemon of the trainer
        userTeam = new ArrayList<>();
        enemyTeam = new ArrayList<>();
        User user = main.getCurrentUser();
        Random rand = new Random();
        paneHelp.setVisible(false);
        btnBack.setDisable(true);

        if (user instanceof Trainer) {
            ArrayList<PokemonMapper> mapper = ((Trainer) user).getTeam();

            for (PokemonMapper p : mapper) {
                userTeam.add(main.getPokemonById(p.getId()));
            }
        }

        //check to see if you can battle, then display the amount of pokeballs according to team size
        if (userTeam.size() == 0) {
            System.out.println("User does not have a team!");
            main.requestSceneChange(SceneManager.sceneName.TRAINERMENU);
            return;
        }

        setUserPokeballsVisible(userTeam.size());

        //setup enemies equal to the trainer's pokemon count
        Pokemon[] allPokemon = main.getAllPokemon().toArray(new Pokemon[main.getAllPokemon().size()]);
        for (int i = 0; i < userTeam.size(); i++) {
            Pokemon pokemon = allPokemon[rand.nextInt(allPokemon.length)];

            System.out.println("Enemy got: " + pokemon.getName());
            enemyTeam.add(pokemon);
        }

        setEnemyPokeballsVisible(enemyTeam.size());

        //select a random pokemon for user and enemy to start battle with
        userPokemon = userTeam.get(rand.nextInt(userTeam.size()));
        enemyPokemon = enemyTeam.get(rand.nextInt(enemyTeam.size()));

        getUserStats();
        getEnemyStats();

        lblPokemonHP.setText(userStats.get(0).toString());
        lblEnemyHP.setText(enemyStats.get(0).toString());

        userHP = userStats.get(0);
        enemyHp = enemyStats.get(0);

        updateLabels();
    }

    private void setUserPokeballsVisible(int count) {
        if (count == 1) {
            teamBall1.setVisible(true);
            teamBall2.setVisible(false);
            teamBall3.setVisible(false);
            teamBall4.setVisible(false);
            teamBall5.setVisible(false);
            teamBall6.setVisible(false);
        } else if (count == 2) {
            teamBall1.setVisible(true);
            teamBall2.setVisible(true);
            teamBall3.setVisible(false);
            teamBall4.setVisible(false);
            teamBall5.setVisible(false);
            teamBall6.setVisible(false);
        } else if (count == 3) {
            teamBall1.setVisible(true);
            teamBall2.setVisible(true);
            teamBall3.setVisible(true);
            teamBall4.setVisible(false);
            teamBall5.setVisible(false);
            teamBall6.setVisible(false);
        } else if (count == 4) {
            teamBall1.setVisible(true);
            teamBall2.setVisible(true);
            teamBall3.setVisible(true);
            teamBall4.setVisible(true);
            teamBall5.setVisible(false);
            teamBall6.setVisible(false);
        } else if (count == 5) {
            teamBall1.setVisible(true);
            teamBall2.setVisible(true);
            teamBall3.setVisible(true);
            teamBall4.setVisible(true);
            teamBall5.setVisible(true);
            teamBall6.setVisible(false);
        } else if (count == 6) {
            teamBall1.setVisible(true);
            teamBall2.setVisible(true);
            teamBall3.setVisible(true);
            teamBall4.setVisible(true);
            teamBall5.setVisible(true);
            teamBall6.setVisible(true);
        } else if (count == 0) {
            teamBall1.setVisible(false);
            teamBall2.setVisible(false);
            teamBall3.setVisible(false);
            teamBall4.setVisible(false);
            teamBall5.setVisible(false);
            teamBall6.setVisible(false);
        }
    }

    private void setEnemyPokeballsVisible(int count) {
        if (count == 1) {
            enemyBall1.setVisible(false);
            enemyBall2.setVisible(false);
            enemyBall3.setVisible(false);
            enemyBall4.setVisible(false);
            enemyBall5.setVisible(false);
            enemyBall6.setVisible(true);
        } else if (count == 2) {
            enemyBall1.setVisible(false);
            enemyBall2.setVisible(false);
            enemyBall3.setVisible(false);
            enemyBall4.setVisible(false);
            enemyBall5.setVisible(true);
            enemyBall6.setVisible(true);
        } else if (count == 3) {
            enemyBall1.setVisible(false);
            enemyBall2.setVisible(false);
            enemyBall3.setVisible(false);
            enemyBall4.setVisible(true);
            enemyBall5.setVisible(true);
            enemyBall6.setVisible(true);
        } else if (count == 4) {
            enemyBall1.setVisible(false);
            enemyBall2.setVisible(false);
            enemyBall3.setVisible(true);
            enemyBall4.setVisible(true);
            enemyBall5.setVisible(true);
            enemyBall6.setVisible(true);
        } else if (count == 5) {
            enemyBall1.setVisible(false);
            enemyBall2.setVisible(true);
            enemyBall3.setVisible(true);
            enemyBall4.setVisible(true);
            enemyBall5.setVisible(true);
            enemyBall6.setVisible(true);
        } else if (count == 6) {
            enemyBall1.setVisible(true);
            enemyBall2.setVisible(true);
            enemyBall3.setVisible(true);
            enemyBall4.setVisible(true);
            enemyBall5.setVisible(true);
            enemyBall6.setVisible(true);
        } else if (count == 0) {
            enemyBall1.setVisible(false);
            enemyBall2.setVisible(false);
            enemyBall3.setVisible(false);
            enemyBall4.setVisible(false);
            enemyBall5.setVisible(false);
            enemyBall6.setVisible(false);
        }
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

        userPokemon = null;
        enemyPokemon = null;
        userTeam = new ArrayList<>();
        enemyTeam = new ArrayList<>();

        btnFight.setVisible(true);
        btnFlee.setVisible(true);
        btnSwitch.setVisible(true);

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

        battleEnded = false;
    }

    public void backToTrainerMenu() {
        main.requestSceneChange(SceneManager.sceneName.TRAINERMENU);
    }

    private boolean isUserAttackingFirst() {
        // Compares Speeds to see if the user is attacking first or not
        return userStats.get(1) >= enemyStats.get(1);
    }

    private void updateMessageBoard() {
        ObservableList<String> readableMessages = FXCollections.observableArrayList(battleMessages);
        messageBoard.setItems(readableMessages);
    }

    private void updateUserHealthBar(Double damage) {
        // A full ProgressBar is value 1 so the damage needs to be less than 1
        damage = damage / 100;

        // Sets the HP of the user to a double in the scale of 1.00 being 100
        Double userProgress = Double.parseDouble(lblPokemonHP.getText());
        userProgress = userProgress / 100;

        userProgress = userProgress - damage;

        hpPokemon.setProgress(userProgress);
    }

    private void updateEnemyHealthBar(Double damage) {
        // A full ProgressBar is value 1 so the damage needs to be less than 1
        damage = damage / 100;

        Double enemyProgress = Double.parseDouble(lblEnemyHP.getText());

        // HP must be in a scale of 1.00 being 100
        enemyProgress = enemyProgress / 100;

        enemyProgress = enemyProgress - damage;

        hpEnemy.setProgress(enemyProgress);
    }

    private void updateLabels() {
        int userHealth = (int) userHP;
        int enemyHealth = (int) enemyHp;

        lblPokemon.setText(userPokemon.getName());
        lblPokemonHP.setText(Integer.toString(userHealth));

        lblEnemy.setText(enemyPokemon.getName());
        lblEnemyHP.setText(Integer.toString(enemyHealth));
    }

    public void attemptEscape() {
        double enemyHP = hpEnemy.getProgress();
        int escapeChance;

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
            main.requestSceneChange(SceneManager.sceneName.TRAINERMENU);
        } else {
            battleMessages.add(lblEnemy.getText() + " won't let you escape!");
            updateMessageBoard();
            escaping = true;
            fightTurn();
        }
    }

    public void fightTurn() {
        //play out one turn, depending on active pokemon speed, enemy or player goes accordingly
        boolean userFirst = isUserAttackingFirst();
        boolean enemyPlayed = false;
        boolean enemyKnockedOut = false;

        if (!userFirst) {
            enemyTurn();
            enemyPlayed = true;
        }

        if (!escaping) {
            double damage;

            // Ask Isak for future system
            // Damage = (Bonus * Attack) - Defense
            damage = (calculateTypeBonus(true) * (userStats.get(2) * (int) ((Math.random() * userStats.get(2)) + 1)) - (enemyStats.get(3) / 2.0));

            // HP = HP - Damage (Damage is rounded to the nearest integer)
            enemyHp = enemyHp - damage;

            // Sets a message that the pokemon attacked
            battleMessages.add(lblPokemon.getText() + " Attacked for: " + damage + " points of damage!");

            updateEnemyHealthBar(damage);

            if (enemyHp <= 0) {
                enemyKnockedOut = true;
                enemyPokemonDefeated(lblEnemy.getText());
            }
        }
        escaping = false;

        if (!enemyPlayed && !battleEnded && !enemyKnockedOut) {
            enemyTurn();
        }

        updateMessageBoard();
        updateLabels();
    }

    private void enemyTurn() {
        //enemies turn to fight the trainer
        double damage = (calculateTypeBonus(false) * (enemyStats.get(2) * (int) ((Math.random() * enemyStats.get(2)) + 1)) - (userStats.get(3) / 2.0));
        battleMessages.add(lblEnemy.getText() + " Attacked for: " + damage + " points of damage!");
        userHP = userHP - damage;

        updateUserHealthBar(damage);

        if (userHP <= 0) {
            userPokemonDefeated(lblPokemon.getText());
        }
    }

    public void switchPokemon() {
        //try to swap out the pokemon for another one of theirs
        if (userTeam.size() == 0) {
            battleMessages.add("You don't have any other available Pokemon");
            updateMessageBoard();
            endBattle();
            return;
        }

        Random rand = new Random();
        Pokemon[] pokemon = userTeam.toArray(new Pokemon[userTeam.size()]);
        userPokemon = pokemon[rand.nextInt(pokemon.length)];

        getUserStats();
        lblPokemonHP.setText(userStats.get(0).toString());
        userHP = userStats.get(0);

        hpPokemon.setProgress(1);

        updateLabels();
    }

    private void enemySwitchPokemon() {
        //enemy switches their currently active pokemon
        if (enemyTeam.size() == 0) {
            battleMessages.add("Enemy doesn't have any other available Pokemon");
            updateMessageBoard();
            endBattle();
            return;
        }

        Random rand = new Random();
        Pokemon[] pokemon = enemyTeam.toArray(new Pokemon[enemyTeam.size()]);
        enemyPokemon = pokemon[rand.nextInt(pokemon.length)];

        getEnemyStats();
        lblEnemyHP.setText(enemyStats.get(0).toString());
        enemyHp = enemyStats.get(0);

        hpEnemy.setProgress(1);

        updateLabels();
    }

    private void getUserStats() {
        userStats.clear();

        userStats.add(userPokemon.getHealth());
        userStats.add(userPokemon.getSpeed());
        userStats.add(userPokemon.getAttack());
        userStats.add(userPokemon.getDefense());

        userType = userPokemon.getType();

        Image pokemon = main.getPokemonImage(userPokemon.getName());
        imgPokemon.setImage(pokemon);
        lblPokemon.setText(userPokemon.getName());
    }

    private void getEnemyStats() {
        enemyStats.clear();

        enemyStats.add(enemyPokemon.getHealth());
        enemyStats.add(enemyPokemon.getSpeed());
        enemyStats.add(enemyPokemon.getAttack());
        enemyStats.add(enemyPokemon.getDefense());

        enemyType = enemyPokemon.getType();

        Image enemy = main.getPokemonImage(enemyPokemon.getName());
        imgEnemy.setImage(enemy);
        lblEnemy.setText(enemyPokemon.getName());
    }

    private void userPokemonDefeated(String name) {
        userTeam.remove(userPokemon);
        setUserPokeballsVisible(userTeam.size());

        battleMessages.add(" ");
        battleMessages.add("Your " + name + " has been knocked Unconscious!");
        battleMessages.add(" ");

        updateMessageBoard();
        switchPokemon();
    }

    private void enemyPokemonDefeated(String name) {
        enemyTeam.remove(enemyPokemon);
        setEnemyPokeballsVisible(enemyTeam.size());

        battleMessages.add(" ");
        battleMessages.add("Enemy " + name + " has been knocked Unconscious!");
        battleMessages.add(" ");

        updateMessageBoard();
        enemySwitchPokemon();
    }

    // WORK IN PROGRESS
    private Double calculateTypeBonus(boolean isUser) {
        // This type is temp ( ͡° ͜ʖ ͡°)
        String tempType;

        if (!isUser) {
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
        User user = main.getCurrentUser();

        if (userTeam.size() > 0 && enemyTeam.size() == 0) {
            ((Trainer) user).setWinCount(((Trainer) user).getWinCount() + 1);
            main.giveUserReward(25);
        } else {
            ((Trainer) user).setLossCount(((Trainer) user).getLossCount() + 1);
        }
        main.updateUserScore();

        btnFight.setVisible(false);
        btnFlee.setVisible(false);
        btnSwitch.setVisible(false);

        battleMessages.add("-------------------------------------------");
        battleMessages.add("The battle is over!");
        battleMessages.add("But you can stay and review what happened");
        battleMessages.add("When you're ready hit the back button");
        battleMessages.add("-------------------------------------------");
        updateMessageBoard();

        battleEnded = true;
        btnBack.setDisable(false);
    }

    public void showHelp() {

        if (help) {
            paneHelp.setVisible(false);
            help = false;
        } else {
            paneHelp.setVisible(true);
            help = true;
        }
    }
}
