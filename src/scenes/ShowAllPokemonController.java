package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.Main;
import pokemon.PokemonMapper;
import users.Trainer;
import users.User;

import java.util.ArrayList;

public class ShowAllPokemonController implements Controller {

    // This Scene will be used by both the professor and the trainer in order to show all the Pokemon in the Database

    private Main main;

    @Override
    public void setMain(Main m) {
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void reset() {

    }

    private int pokemonSelected; // Pokemon Selected should be max value the number of Pokemon in our database!!
    private String nickname;

    @FXML
    ListView<String> listView;

    @FXML
    Label labelTop;

    public void showPokemonCollection() {
        User user = main.getCurrentUser();

        ArrayList<PokemonMapper> collection = ((Trainer) user).getCollection();
        ArrayList<String> pokeNames = new ArrayList<>();
        for (PokemonMapper poke : collection) {
            pokeNames.add(main.getPokemon(poke.getId()).getName());
        }

        // Needed for a ListView in JavaFX for some reason
        ObservableList<String> collectionPokemon = FXCollections.observableArrayList(pokeNames);

        listView.setItems(collectionPokemon);

    }

    // Once it has been named use this method to store it
    public void acquirePokemon() {
        User user = main.getCurrentUser();
        if (user instanceof Trainer) {
            // Added for extra security change the values depending on highest ID number of pokemon the database has!  CURRENT HIGHEST = RAICHU 26
            if (pokemonSelected > 0 && pokemonSelected <= 26) {
                // Taken from the main ********************************************* MIGHT NOT BE THE CORRECT WAY TO ADD
                PokemonMapper pokemon = new PokemonMapper(pokemonSelected, nickname);
                ((Trainer) user).addToCollection(pokemon);
            }
        }
    }
}
