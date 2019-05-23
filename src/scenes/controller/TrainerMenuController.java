package scenes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;
import main.scenemanager.SceneManager;
import main.users.Trainer;
import main.users.User;

public class TrainerMenuController implements Controller {

    // The Trainer Menu is just that a menu that contains different buttons that take you to new scenes

    private Main main;


    @Override
    public void setMain(Main m) {
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void setUp(){
        updateStats();
    }

    @Override
    public void reset() {

    }

    @FXML
    Button btnCollection, btnBattle, btnFindPokemon;

    @FXML
    Label lblWins, lblLosses, lblCurrency, labelWelcomeTrainer;

    private void updateStats(){
        User user = main.getCurrentUser();

        int currency = ((Trainer) user).getCurrency();
        int wins = ((Trainer) user).getWinCount();
        int losses = ((Trainer) user).getLossCount();

        labelWelcomeTrainer.setText("Welcome: " + ((Trainer) user).getUsername());
        lblCurrency.setText(((Integer) currency).toString());
        lblWins.setText(((Integer) wins).toString());
        lblLosses.setText(((Integer) losses).toString());
    }

    // All of these methods change the Scene to a new one and nothing else

    public void viewCollection() {
        main.requestSceneChange(SceneManager.sceneName.TRAINERCOLLECTION);
    }

    public void viewBattle() {
        main.requestSceneChange(SceneManager.sceneName.BATTLEMAIN);
    }

    public void viewFindPokemon() {
        main.requestSceneChange(SceneManager.sceneName.SHOWALLPOKEMON);
    }

    public void logoutButton() {
        main.logoutUser();
    }

}
