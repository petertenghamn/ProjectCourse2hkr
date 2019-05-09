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
import users.Trainer;
import users.User;

import java.util.*;

public class TrainerMenuController implements Controller {

    @FXML
    Label lblTop;

    @FXML
    Button btnCollection, btnAddPokemon, btnSelect;

    @FXML
    ListView<String> lvCollection ;

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    public void reset(){

    }

    private Main main;


    // This method should be hidden behind the "buy" feature later on
    public void showAllPokemon(){
        // Re-Arranges the scene to the selecting a new pokemon
        btnSelect.setVisible(true);
        lblTop.setText("Here are the Available Pokemons!");

        lvCollection.setOrientation(Orientation.VERTICAL);

        Pokemon[] pokemons = main.getAllPokemon();
        ArrayList<String> tempNameString = new ArrayList<>();

        for (int showAll = 0; showAll < pokemons.length; showAll++){
            tempNameString.add(pokemons[showAll].getName());
        }

        // Observable Lists are the only list available to be used by the ListView so...
        ObservableList<String>  pokemonNames = FXCollections.observableArrayList(tempNameString);

        lvCollection.getItems().addAll(pokemonNames);
    }

    // Opens the Scene AcquirePokemon
    public void boughtPokemon(){

    }

    public void showPokemonCollection(){
        User user = main.getCurrentUser();
        if (user instanceof Trainer){
            PokemonMapper[] collection = ((Trainer) user).getCollection();
            ArrayList<String> pokeNames = new ArrayList<>();
            for (PokemonMapper poke : collection){
                pokeNames.add(main.getPokemon(poke.getId()).getName());
            }

            // Needed for a ListView in JavaFX for some reason
            ObservableList<String> collectionPokemon = FXCollections.observableArrayList(pokeNames);

            lvCollection.setItems(collectionPokemon);
        }
    }

    public void logoutButton(){
        main.logoutUser();
    }
}
