package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.Main;
import pokemon.PokemonMapper;
import scenemanager.SceneManager;
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

    // NOT YET IMPLEMENTED
    public void viewCollection(){
        // This will go inside the scene not here!
                /*
        User user = main.getCurrentUser();

        ArrayList<PokemonMapper> collection = ((Trainer) user).getCollection();
        ArrayList<String> pokeNames = new ArrayList<>();
        for (PokemonMapper poke : collection) {
            pokeNames.add(main.getPokemon(poke.getId()).getName());
        }

        // Needed for a ListView in JavaFX for some reason
        ObservableList<String> collectionPokemon = FXCollections.observableArrayList(pokeNames);

        listView.setItems(collectionPokemon);
        */
    }

    public void viewBattle(){

    }

    public void viewFindPokemon(){
        main.requestSceneChange(SceneManager.sceneName.SHOWALLPOKEMON);
    }

    public void logoutButton(){
        main.logoutUser();
    }

}
