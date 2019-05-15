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

import java.util.ArrayList;

public class Main extends Application {

    private DatabaseLoader pokeDB;
    private SceneManager manager;

    //stored user who has logged in
    private User currentUser;

    //stored list of all pokemon retrieved from the database
    private Pokemon[] allPokemon;

    /*
     * Get the full list of all pokemon currently stored
     *
     * @returns Pokemon[]
     */
    public Pokemon[] getAllPokemon(){
        return allPokemon;
    }

    /*
     * Get a pokemon using the id the pokemon has
     *
     * @returns Pokemon
     */
    public Pokemon getPokemonById(int id){
        for (Pokemon pID : allPokemon){
            if (pID.getIdTag() == id){
                return pID;
            }
        }
        return null;
    }

    /*
     * Get a pokemon using the name of the pokemon
     * Be careful when implementing this, can have a spelling mistake that cause errors
     *
     * @returns Pokemon
     */
    public Pokemon getPokemonByName(String name){
        for (Pokemon pN: allPokemon){
            if(pN.getName().equalsIgnoreCase(name)) {
                return pN;
            }
        }
        return null;
    }

    /*
     * Get the user that is logged in
     *
     * @returns User
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /*
     * Method ran at program start (Initializes important classes)
     */
    @Override
    public void start(Stage primaryStage) {
        pokeDB = new DatabaseLoader();
        allPokemon = pokeDB.loadAllPokemon();
        manager = new SceneManager(this, primaryStage);
    }

    /*
     * Main method, Java startup
     */
    public static void main(String[] args) {
        launch(args);
    }

    /*
     * Change the scene to the requested scene
     */
    public void requestSceneChange(SceneManager.sceneName scene){
        manager.changeScene(scene);
    }

    /*
     * Logout the user and return to login screen
     */
    public void logoutUser(){
        if (currentUser != null) {
            currentUser = null;
        }
        manager.changeScene(SceneManager.sceneName.LOGIN);
    }

    /*
     * Calls to the DB to verify the information entered is correct
     * Only changes screen if information is matched to a user in the DB
     */
    public void authenticateLogin(String email, String password){
        currentUser = pokeDB.authenticateLogin(email, password);
        if (currentUser != null) {
            if (currentUser instanceof Trainer){
                manager.changeScene(SceneManager.sceneName.TRAINERMENU);
            }
            else if (currentUser instanceof Professor){
                manager.changeScene(SceneManager.sceneName.PROFESSORMENU);
            }
        }
        else {
            System.out.println("Information entered was incorrect!");
        }
    }

    /*
     * Check that the email is unique then create a new user class with entered information
     * This does not add the user to the DB yet as the creation can still be canceled
     */
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

    /*
     * Assign the selected pokemon to the new trainer and finalize by adding the trainer to the DB
     */
    public void acquirePokemon(int pokemonID, String nickname){
        ArrayList<PokemonMapper> collection = ((Trainer) currentUser).getCollection();

        //cannot have a null name when inserting into DB
        if (nickname == null) {
            nickname = getPokemonById(pokemonID).getName();
        }

        for (PokemonMapper mappedPokemon : collection){
            // Checks if there are pokemon with that nickname in your collection already
            if (!mappedPokemon.getNickname().equalsIgnoreCase(nickname)) {
                //attach the selected starter to the trainer that choose it, then proceed to trainer menu
                PokemonMapper caughtPokemon = new PokemonMapper(pokemonID, nickname);
                ((Trainer) currentUser).addToCollection(caughtPokemon);
                break; // Needed or else it breaks the for loop
            }
            else {
                System.out.println("There is already a Pokemon with that name in your collection: " + nickname);
            }
        }

        // Later will move this part of the code
        if (((Trainer) currentUser).getCollection().size() == 0){
            pokeDB.createNewUser(currentUser);
            manager.changeScene(SceneManager.sceneName.TRAINERMENU);
        }
    }

    /*
     * Load the image of the pokemon requested
     *
     * @returns Image
     */
    public Image getPokemonImage(String pokemonName){
        // Set default image, can't return null if the pokemon image is missing
        Image image = new Image("scenes/view/images/pokeLogo.png");

        try {
            image = new Image("scenes/view/images/" + getPokemonByName(pokemonName).getName().toLowerCase() + ".png");
        } catch (Exception e){
            System.out.println("Missing image for - " + getPokemonByName(pokemonName).getName().toLowerCase());
        }

        return image;
    }
}
