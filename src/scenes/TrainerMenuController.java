package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.Main;
import pokemon.Pokemon;
import pokemon.PokemonMapper;
import scenes.Controller;
import users.Trainer;
import users.User;

import java.util.*;

public class TrainerMenuController implements Controller {

    // The Trainer Menu is just that a menu that contains different buttons that take you to new scenes

    private Main main;

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    public void reset(){

    }

    @FXML
    Button btnCollection, btnBattle, btnFindPokemon;

    // All of these methods change the Scene to a new one!

    public void viewCollection(){

    }

    public void viewBattle(){

    }

    public void viewFindPokemon(){

    }

    // *********************************************************************************
    // THIS WILL MOVE TO A NEW SCENE

    @FXML
    ListView<String> listCollection ;

    @FXML
    Label labelTop;


    public void showPokemonCollection(){
        User user = main.getCurrentUser();
        if (user instanceof Trainer){
            ArrayList<PokemonMapper> collection = ((Trainer) user).getCollection();
            ArrayList<String> pokeNames = new ArrayList<>();
            for (PokemonMapper poke : collection){
                pokeNames.add(main.getPokemon(poke.getId()).getName());
            }

            // Needed for a ListView in JavaFX for some reason
            ObservableList<String> collectionPokemon = FXCollections.observableArrayList(pokeNames);

            listCollection.setItems(collectionPokemon);
        }
    }

    private int pokemonSelected = main.getPokemonIDGlobal(); // Pokemon Selected should be max value the number of Pokemon in our database!!
    private String nickname = main.getPokemonNicknameGlobal();

    // Once it has been named use this method to store it
    public void acquirePokemon(){
        User user = main.getCurrentUser();
        if (user instanceof Trainer){
            // Added for extra security change the values depending on highest ID number of pokemon the database has!  CURRENT HIGHEST = RAICHU 26
            if (pokemonSelected > 0 && pokemonSelected <= 26) {
                // Taken from the main ********************************************* MIGHT NOT BE THE CORRECT WAY TO ADD
                PokemonMapper pokemon = new PokemonMapper(pokemonSelected, nickname);
                ((Trainer) user).addToCollection(pokemon);
            }
        }
    }

    // ********************************************************************************************

    public void logoutButton(){
        main.logoutUser();
    }
}
