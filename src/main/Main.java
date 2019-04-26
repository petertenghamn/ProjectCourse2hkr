package main;

//not importing database loader caused errors... not sure why...
import main.DatabaseLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import pokemon.Pokemon;
import scenemanager.SceneManager;
import users.Professor;
import users.Trainer;
import users.User;

public class Main extends Application {

    private DatabaseLoader pokeDB;
    private SceneManager manager;

    private User currentUser;

    public Pokemon[] getAllPokemon(){
        return pokeDB.getAllPokemon();
    }
    public Pokemon getPokemon(int id){
        return pokeDB.getPokemon(id);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //create connection with server
        pokeDB = new DatabaseLoader();
        pokeDB.loadAllPokemon();
        pokeDB.testFunction();

        manager = new SceneManager(this, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    //if the scene request is simple and just wants to transition with no checks
    public void requestSceneChange(SceneManager.sceneName scene){
        manager.changeScene(scene);
    }

    public void logoutUser(){
        //reset current user then return to the login screen;
        if (currentUser != null) {
            currentUser = null;
        }
        manager.changeScene(SceneManager.sceneName.LOGIN);
    }

    public void authenticateLogin(String email, String password){
        //check login details vs database
        currentUser = pokeDB.authenticateLogin(email, password);
        if (currentUser != null) {
            //change to trainer controller if that is the user logged into
            if (currentUser instanceof Trainer){
                manager.changeScene(SceneManager.sceneName.TRAINERMENU);
            }
            //change to professor controller if that is the user logged into
            else if (currentUser instanceof Professor){
                manager.changeScene(SceneManager.sceneName.PROFESSORMENU);
            }
        }
        else {
            System.out.println("Information entered was incorrect!");
        }
    }

    public void createNewUser(String email, String password){
        //creates a new user class of trainer to store info in, then transitions to selecting a starter
        if (pokeDB.checkIfEmailAvailable(email)) {
            currentUser = new Trainer(email, 0, 0, 0, new Pokemon[0], new Pokemon[0]);
            ((Trainer) currentUser).setNewUserPassword(password);

            manager.changeScene(SceneManager.sceneName.SELECTSTARTER);
        }
        else {
            System.out.println("Email already in use!");
        }
    }

    public void selectedStarter(Pokemon starter){
        //attach the selected started to the trainer that choose it, then proceed to trainer menu
        ((Trainer) currentUser).addToCollection(starter);
        pokeDB.createNewUser(currentUser);
        manager.changeScene(SceneManager.sceneName.TRAINERMENU);
    }
}
