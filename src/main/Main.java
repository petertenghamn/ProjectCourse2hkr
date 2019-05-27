package main;

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
    private DebugDatabase pokeBugDB;
    private ArrayList<User> debugUsers;
    private boolean deBugLoader = false;

    public void setDeBugLoader(boolean deBugLoader) {
        this.deBugLoader = deBugLoader;
    }

    public boolean isDeBugLoader() {
        return deBugLoader;
    }

    //stored user who has logged in
    private User currentUser;

    //stored list of all pokemon retrieved from the database
    private ArrayList<Pokemon> allPokemon;

    //List of All Trainers in the program
    private ArrayList<User> allTrainers;

    /*
     * Main method, Java startup
     */
    public static void main(String[] args) {
        launch(args);
    }

    /*
     * Get the full list of all pokemon currently stored
     *
     * @returns Pokemon[]
     */
    public ArrayList<Pokemon> getAllPokemon() {
        return allPokemon;
    }
      
    public void setAllPokemon() {
        allPokemon = pokeBugDB.getPokemons();
    }

    /*
     * Get a pokemon using the id the pokemon has
     *
     * @returns Pokemon
     */
    public Pokemon getPokemonById(int id) {
        for (Pokemon pID : allPokemon) {
            if (pID.getIdTag() == id) {
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
    public Pokemon getPokemonByName(String name) {
        for (Pokemon pN : allPokemon) {
            if (pN.getName().equalsIgnoreCase(name)) {
                return pN;
            }
        }
        return null;
    }

    /*
     * Add a pokemon to the pokeDB
     * First checks to see that the pokemon does not already exist
     *
     * @returns boolean (false if failed to add)
     */
    public boolean addPokemon(Pokemon pokemon){
        for (Pokemon p : allPokemon){
            if (p.getIdTag() == pokemon.getIdTag() ||
                    p.getName().equals(pokemon.getName())){
                return false;
            }
        }

        allPokemon.add(pokemon);
        pokeDB.addPokemon(pokemon);
        return true;
    }

    /*
     * Edit a pokemon in the pokeDB
     * First checks to see that the pokemon does exist
     *
     * @returns boolean (false if failed to edit)
     */
    public boolean editPokemon(Pokemon pokemon){
        for (Pokemon p : allPokemon){
            if (p.getIdTag() == pokemon.getIdTag()){
                allPokemon.remove(p);
                allPokemon.add(pokemon);
                pokeDB.editPokemon(pokemon);
                return false;
            }
        }

        return false;
    }

    /*
     * Remove a pokemon from the pokeDB
     * First checks to see that the pokemon does exist
     *
     * @returns boolean (false if failed to remove)
     */
    public boolean removePokemon(Pokemon pokemon){
        for (Pokemon p : allPokemon){
            if (p.getIdTag() == pokemon.getIdTag() &&
                    p.getName().equals(pokemon.getName())){

                allPokemon.remove(p);
                pokeDB.removePokemon(p);
                return false;
            }
        }

        return false;
    }

    /*
     * Method ran at program start (Initializes important classes)
     */
    @Override
    public void start(Stage primaryStage) {
        pokeDB = new DatabaseLoader();
        pokeBugDB = new DebugDatabase();
        manager = new SceneManager(this, primaryStage);
    }

    /*
     * Change the scene to the requested scene
     */
    public void requestSceneChange(SceneManager.sceneName scene) {
        manager.changeScene(scene);
    }

    /*
     * Logout the user and return to login screen
     */
    public void logoutUser() {
        if (currentUser != null) {
            currentUser = null;
        }
        manager.changeScene(SceneManager.sceneName.LOGIN);
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
     * Check to see if the user should get a login bonus
     */
    public void loginBonusCheck(){
        if (currentUser instanceof Trainer) {
            if (pokeDB.loginBonusCheck(((Trainer) currentUser).getEmail())){
                ((Trainer) currentUser).recieveLoginBonus();
                pokeDB.updateUserCurrency(currentUser);

                //create popup window
                manager.showLoginBonus();
                System.out.println("Trainer received login bonus!");
            }
            else
            {
                System.out.println("Trainer not eligible for login bonus!");
            }
        }
    }

    /*
     * Update the current users score in the DB
     */
    public void giveUserReward(int amount){
        if (currentUser instanceof Trainer){
            ((Trainer) currentUser).reward(amount);
        }
        pokeDB.updateUserCurrency(currentUser);
    }

    /*
     * Update the current users score in the DB
     */
    public void updateUserScore(){
        pokeDB.updateUserScore(currentUser);
    }

    /*
     * Calls to the DB to verify the information entered is correct
     * Only changes screen if information is matched to a user in the DB
     *
     * If user = trainer, then login bonus is checked and given accordingly
     */
    public boolean authenticateLogin(String email, String password) {
        if (!deBugLoader) {
            currentUser = pokeDB.authenticateLogin(email, password);
        } else {
            currentUser = pokeBugDB.authenticateUser(email, password);
        }

        if (currentUser != null) {
            if (currentUser instanceof Trainer) {
                allPokemon = pokeDB.loadAllPokemon();
                manager.changeScene(SceneManager.sceneName.TRAINERMENU);

                //for testing
                System.out.printf("%n%s%n%s%n%s%n%n", "For Testing", "Login bonus always is shown on login of trainer", "Disable in line ~241 of main");
                manager.showLoginBonus();

            } else if (currentUser instanceof Professor) {
                allPokemon = pokeDB.loadAllPokemon();
                allTrainers = pokeDB.getTrainers();
                manager.changeScene(SceneManager.sceneName.PROFESSORMENU);
            }
            return true;
        } else {
            return false;
        }
    }

    /*
     * Check that the email is unique then create a new user class with entered information
     * This does not add the user to the DB yet as the creation can still be canceled
     */
    public void createNewUser(String email, String username, String password) {
        //creates a new user class of trainer to store info in, then transitions to selecting a starter
        if (pokeDB.checkIfEmailAvailable(email)) {
            currentUser = new Trainer(email, username, 0, 0, 0, new ArrayList<PokemonMapper>(), new ArrayList<PokemonMapper>());
            ((Trainer) currentUser).setNewUserPassword(password);

            manager.changeScene(SceneManager.sceneName.SELECTSTARTER);
        } else {
            System.out.println("Email already in use!");
        }
    }

    /*
     * Assign the selected pokemon to the new trainer and finalize by adding the trainer to the DB
     */
    public boolean acquirePokemon(int pokemonID, String nickname, boolean checkCost) {
        if (currentUser instanceof Trainer) {
            ArrayList<PokemonMapper> collection = ((Trainer) currentUser).getCollection();

            // checks to see if its adding the first pokemon
            if (collection.size() == 0) {
                pokeDB.createNewUser(currentUser);
                manager.changeScene(SceneManager.sceneName.TRAINERMENU);
            }

            //cannot have a null name when inserting into DB
            if (nickname == null) {
                nickname = getPokemonById(pokemonID).getName();
            }

            //check if nickname is already being used
            boolean exists = false;
            for (PokemonMapper mappedPokemon : collection) {
                if (mappedPokemon.getNickname().equals(nickname)) {
                    exists = true;
                }
            }

            //give result
            if (!exists) {
                if (checkCost) {
                    int cost = pokeDB.getPokemonCost(pokemonID);
                    int userMoney = ((Trainer) currentUser).getCurrency();
                    if (userMoney >= cost) {
                        ((Trainer) currentUser).pay(cost);
                        PokemonMapper caughtPokemon = new PokemonMapper(pokemonID, nickname);
                        ((Trainer) currentUser).addToCollection(caughtPokemon);

                        pokeDB.updateUserCurrency(currentUser);
                        pokeDB.addPokemonUserCollection(currentUser, caughtPokemon);
                    } else {
                        System.out.println("You cannot afford: " + cost);
                        return false;
                    }
                } else {
                    PokemonMapper caughtPokemon = new PokemonMapper(pokemonID, nickname);
                    ((Trainer) currentUser).addToCollection(caughtPokemon);
                    pokeDB.addPokemonUserCollection(currentUser, caughtPokemon);
                }
            } else {
                System.out.println("There is already a Pokemon with that name in your collection: " + nickname);
                return false;
            }
        }
        return true;
    }

    /*
     * Add pokemon to user's team
     */
    public void addPokemonUserTeam(PokemonMapper pokemon){
        pokeDB.addPokemonUserTeam(currentUser, pokemon);
    }

    /*
     * Change one pokemon for another in user's team
     */
    public void exchangePokemonUserTeam(PokemonMapper pokemon, PokemonMapper oldPokemon){
        pokeDB.exchangePokemonUserTeam(currentUser, pokemon, oldPokemon);
    }

    /*
     * Remove pokemon from user's team
     */
    public void removePokemonUserTeam(PokemonMapper pokemon){
        pokeDB.removePokemonUserTeam(currentUser, pokemon);
    }

    /*
     * Load the image of the pokemon requested
     *
     * @returns Image
     */
    public Image getPokemonImage(String pokemonName) {
        // Set default image, can't return null if the pokemon image is missing
        Image image = new Image("scenes/view/images/pokeLogo.png");

        try {
            image = new Image("scenes/view/images/" + getPokemonByName(pokemonName).getName().toLowerCase() + ".png");
        } catch (Exception e) {
            System.out.println("Missing image for - " + getPokemonByName(pokemonName).getName().toLowerCase());
        }

        return image;
    }

    public ArrayList<User> getTrainers(){
        return allTrainers;
    }
}
