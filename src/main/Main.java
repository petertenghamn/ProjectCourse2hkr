package main;

//not importing database loader caused errors... not sure why...
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.stage.Stage;
import main.pokemon.Pokemon;
import main.pokemon.PokemonMapper;
import main.scenemanager.SceneManager;
import main.users.Professor;
import main.users.Trainer;
import main.users.User;

public class Main extends Application {

    private DatabaseLoader pokeDB;
    private SceneManager manager;

    private User currentUser;

    private Pokemon[] allPokemon;

    //all getter methods
    public Pokemon[] getAllPokemon(){
        return allPokemon;
    }

    public Pokemon getPokemonById(int id){
        for (Pokemon pID : allPokemon){
            if (pID.getIdTag() == id){
                return pID;
            }
        }
        return null;
    }

    public Pokemon getPokemonByName(String name){
        for (Pokemon pN: allPokemon){
            if(pN.getName() == name) {
                return pN;
            }
        }
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //create connection with server
        pokeDB = new DatabaseLoader();
        allPokemon = pokeDB.loadAllPokemon();
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

    public void createNewUser(String email, String username, String password){
        //creates a new user class of trainer to store info in, then transitions to selecting a starter
        if (pokeDB.checkIfEmailAvailable(email)) {
            currentUser = new Trainer(email, username, 0, 0, 0, new PokemonMapper[0], new PokemonMapper[0]);
            ((Trainer) currentUser).setNewUserPassword(password);

            manager.changeScene(SceneManager.sceneName.SELECTSTARTER);
        }
        else {
            System.out.println("Email already in use!");
        }
    }

    public void selectedStarter(int starterID, String nickname){
        //cannot have a null name when inserting into DB
        if (nickname == null) {
            nickname = getPokemonById(starterID).getName();
        }

        //attach the selected starter to the trainer that choose it, then proceed to trainer menu
        PokemonMapper starter = new PokemonMapper(starterID, nickname);
        ((Trainer) currentUser).addToCollection(starter);
        pokeDB.createNewUser(currentUser);
        manager.changeScene(SceneManager.sceneName.TRAINERMENU);
    }

    public Image getPokemonImage(Integer pokemonID){
        // Sets the image to a pokemon without an image to the application logo
        Image image = new Image("scenes/images/pokeLogo.png");

        // ******************* THIS IS NOT COMPLETE ************ SOME POKEMON DON'T HAVE AN IMAGE YET! **************
        if (pokemonID == 1) {
            try {
                image = new Image("scenes/images/bulbasaur.png");
            } catch (Exception e) {
                System.out.println("Error finding Image Path!");
            }
        } else if (pokemonID == 4) {
            try {
                image = new Image("scenes/images/charmander.png");
            } catch (Exception e) {
                System.out.println("Error finding Image Path!");
            }
        } else if (pokemonID == 7) {
            try {
                image = new Image("scenes/images/squirtle.png");
            } catch (Exception e) {
                System.out.println("Error finding Image Path!");
            }
        } else {
            System.out.println("No main.pokemon Selected");
            System.out.println("Or Pokemon doesn't have a Picture");
        }

        return image;
    }
}
