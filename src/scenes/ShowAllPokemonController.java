package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import pokemon.Pokemon;
import scenemanager.SceneManager;
import users.Professor;
import users.Trainer;
import users.User;

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
        showStats(listView.getSelectionModel().getSelectedIndex() + 1);
        // Don't know if it's better to use the name of the pokemon here or not let me know! - Öjvind
        showImage(listView.getSelectionModel().getSelectedIndex() + 1);
    }

    // Shows the Image of the Pokemon based on the pokemon's ID
    private void showImage(Integer selection) {
        // Moved the code to main so it could be used by other methods around the application
        imageView.setImage(main.getPokemonImage(selection));
    }

    // Would be great if we could figure out how to reuse this code instead of re writing it again.
    // This won't work if the pokemons stats change by level and not by evolution!
    private void showStats(Integer selection) {
        lblName.setText(main.getPokemonById(selection).getName());
        lblType.setText(main.getPokemonById(selection).getType());
        lblID.setText(Integer.toString(main.getPokemonById(selection).getIdTag()));
        lblHP.setText(Integer.toString(main.getPokemonById(selection).getHealth()));
        lblAtk.setText(Integer.toString(main.getPokemonById(selection).getAttack()));
        lblDf.setText(Integer.toString(main.getPokemonById(selection).getDefense()));
        lblSpeed.setText(Integer.toString(main.getPokemonById(selection).getSpeed()));
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
    /*  WORK IN PROGRESS
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

        Pokemon[] allPokemon = main.getAllPokemon();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Integer> hps = new ArrayList<>();
        ArrayList<Integer> dfs = new ArrayList<>();
        ArrayList<Integer> atks = new ArrayList<>();

        for (Pokemon pokemon: allPokemon) {
            names.add(pokemon.getName());
            types.add(pokemon.getType());
            ids.add(pokemon.getIdTag());
            hps.add(pokemon.getIdTag());
            dfs.add(pokemon.getDefense());
            atks.add(pokemon.getAttack());
        }
    }
    */
}
