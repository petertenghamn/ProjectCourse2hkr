package main;

import javafx.application.Application;
import javafx.stage.Stage;
import scenemanager.SceneManager;

public class Main extends Application {

    DatabaseLoader pokeDB;
    SceneManager manager;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //create connection with server
        pokeDB = new DatabaseLoader();
        pokeDB.TestFunction();

        manager = new SceneManager(primaryStage);
        manager.loadScenes();
        manager.changeScene(SceneManager.sceneName.SELECTSTARTER);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
