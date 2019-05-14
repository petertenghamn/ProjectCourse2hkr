package scenes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import main.Main;
import main.pokemon.Pokemon;
import main.scenemanager.SceneManager;
import main.users.Professor;
import main.users.Trainer;
import main.users.User;

import java.util.ArrayList;

public class ShowAllPokemonController implements Controller {

    // This Scene will be used by both the professor and the trainer in order to show all the Pokemon in the Database
    // The trainer will be able to then be able to see the "buy" button while the professor can't

    private Main main;

    @Override
    public void setMain(Main m) {
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void setUp(){
    }

    @Override
    public void reset() {

    }

    @FXML
    ListView<String> listView;

    @FXML
    Label lblName, lblID, lblType, lblHP, lblAtk, lblDf, lblSpeed;

    @FXML
    ImageView imageView;

    public void showPokemonCollection() {
        Pokemon[] allPokemon = main.getAllPokemon();
        ArrayList<String> pokemonNames = new ArrayList<>();

        for (Pokemon pokemon : allPokemon) {
            pokemonNames.add(pokemon.getName());
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);
        listView.setItems(observableListPokemons);
    }

    public void showPokemon() {
        // THE ONLY POKEMON IMPLEMENTED FULLY SO FAR ARE THE THREE STARTERS! AS A PROOF OF CONCEPT
        showStats(listView.getSelectionModel().getSelectedItem());
        // Don't know if it's better to use the name of the pokemon here or not let me know! - Öjvind
        showImage(listView.getSelectionModel().getSelectedItem());
    }

    // Shows the Image of the Pokemon based on the pokemon's ID
    private void showImage(String selection) {
        // Moved the code to main so it could be used by other methods around the application
        imageView.setImage(main.getPokemonImage(selection));
    }

    // Would be great if we could figure out how to reuse this code instead of re writing it again.
    // This won't work if the pokemons stats change by level and not by evolution!
    private void showStats(String selection) {
        lblName.setText(main.getPokemonByName(selection).getName());
        lblType.setText(main.getPokemonByName(selection).getType());
        lblID.setText(Integer.toString(main.getPokemonByName(selection).getIdTag()));
        lblHP.setText(Integer.toString(main.getPokemonByName(selection).getHealth()));
        lblAtk.setText(Integer.toString(main.getPokemonByName(selection).getAttack()));
        lblDf.setText(Integer.toString(main.getPokemonByName(selection).getDefense()));
        lblSpeed.setText(Integer.toString(main.getPokemonByName(selection).getSpeed()));
    }

    public void backButton() {
        User user = main.getCurrentUser();
        if (user instanceof Trainer) {
            main.requestSceneChange(SceneManager.sceneName.TRAINERMENU);
        } else if (user instanceof Professor) {
            main.requestSceneChange(SceneManager.sceneName.PROFESSORMENU);
        } else {
            System.out.println("Something is very wrong! User is not a Trainer or Professor!");
        }
    }

    // THIS PART ONWARDS IS ONLY TO BE USED BY THE TRAINER!
    // Once it has been named use this method to store it
    public void acquirePokemon() {

    }

    private void reArrangeScene(){
        
    }
}
