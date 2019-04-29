package scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import main.Main;
import pokemon.Pokemon;
import scenemanager.SceneManager;

import java.util.ArrayList;

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
        ArrayList<Pokemon> pokemons = main.getPokemonArray();
        Pokemon pokemon = pokemons.get(0);
        listView.getItems().addAll(pokemon.getName());
    }

    public void logoutButton(){
        main.logoutUser();
    }
}
