package main;

//not importing database loader caused errors... not sure why...
import main.DatabaseLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import pokemon.Pokemon;
import scenemanager.SceneManager;
import users.User;

public class Main extends Application {

    private DatabaseLoader pokeDB;
    private SceneManager manager;

    private User currentUser;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //create connection with server
        pokeDB = new DatabaseLoader();
        pokeDB.TestFunction();

        manager = new SceneManager(this, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    //if the scene request is simple and just wants to transition with no checks
    public void requestSceneChange(SceneManager.sceneName scene){
        manager.changeScene(scene);
    }

    public void AuthenticateLogin(String email, String password){
        //check the user login details before changing scenes
        //*****TEMPORARY
        if (email.equalsIgnoreCase("student@hkr") && password.equalsIgnoreCase("12345")){
            manager.changeScene(SceneManager.sceneName.TRAINERMENU);
        }
        else {
            System.out.println("Temporary: Email = student@hkr, Password = 12345");
        }
        //check vs database
        //change to trainer controller if that is the user logged into
        //change to professor controller if that is the user logged into
    }

    public void createNewUser(String email, String password){
        //creates a new user class of trainer to store info in, then transitions to selecting a starter
        System.out.println("TODO: need logic to make info into a new user and set all other defaults");

        manager.changeScene(SceneManager.sceneName.SELECTSTARTER);
    }

    public void selectedStarter(Pokemon starter){
        //attach the selected started to the trainer that choose it, then proceed to trainer menu
        System.out.println("TODO: need logic to attach starter selected to the user");

        manager.changeScene(SceneManager.sceneName.TRAINERMENU);
    }
}
