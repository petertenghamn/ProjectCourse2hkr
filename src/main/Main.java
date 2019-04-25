package main;

//not importing database loader caused errors... not sure why...
import main.DatabaseLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import scenemanager.SceneManager;

public class Main extends Application {

    private DatabaseLoader pokeDB;
    private SceneManager manager;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //create connection with server
        pokeDB = new DatabaseLoader();
        pokeDB.TestFunction();

        manager = new SceneManager(primaryStage);
        manager.loadScenes();
        manager.changeScene(SceneManager.sceneName.LOGIN);
        manager.setMainToController(this);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void loginRequested(){
        System.out.println("Login request made!");
    }
}
