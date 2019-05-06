package scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import main.Main;
import pokemon.PokemonMapper;
import users.Trainer;
import users.User;

import java.util.*;

public class TrainerMenuController implements Controller {

    @FXML
    Button btnCollection;

    @FXML
    ListView listView;

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    public void reset(){

    }

    private Main main;

    public void showPokemonCollection(){
        User user = main.getCurrentUser();
        if (user instanceof Trainer){
            PokemonMapper[] collection = ((Trainer) user).getCollection();
            ArrayList<String> pokeNames = new ArrayList<>();
            for (PokemonMapper poke : collection){
                pokeNames.add(main.getPokemon(poke.getId()).getName());
            }
            listView.getItems().setAll(pokeNames);
        }
    }

    public void logoutButton(){
        main.logoutUser();
    }
}
