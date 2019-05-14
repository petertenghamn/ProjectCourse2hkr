package scenes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.Main;
import main.scenemanager.SceneManager;

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

    }

    @Override
    public void reset() {

    }

    @FXML
    Button btnCollection, btnBattle, btnFindPokemon;


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
