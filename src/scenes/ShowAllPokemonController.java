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
        showImage(listView.getSelectionModel().getSelectedIndex() + 1);
    }

    private void showImage(Integer selection) {
        // Sets the image before any pokemon has been selected
        Image image = new Image("scenes/pokepictures/pokeLogo.png");

        // ******************* THIS IS NOT COMPLETE ************ SOME POKEMON DON'T HAVE AN IMAGE YET! **************
        // Can later be changed to be based on the Index of selected Item once database is more complete and the index represents the pokemonID
        // Bulbasaur is the only one that works correctly! Later on you won't have to put the name of the pokemon
        if (selection == 1) {
            try {
                image = new Image("scenes/pokepictures/bulbasaur.png");
            } catch (Exception e) {
                System.out.println("Error finding Image Path!");
            }
        } else if (listView.getSelectionModel().getSelectedItem().equalsIgnoreCase("charmander")) {
            try {
                image = new Image("scenes/pokepictures/charmander.png");
            } catch (Exception e) {
                System.out.println("Error finding Image Path!");
            }
        } else if (listView.getSelectionModel().getSelectedItem().equalsIgnoreCase("squirtle")){
            try {
                image = new Image("scenes/pokepictures/squirtle.png");
            } catch (Exception e) {
                System.out.println("Error finding Image Path!");
            }
        }
        else {
            // Might Want to change to a label later
            System.out.println("No pokemon Selected");
            System.out.println("Or Pokemon doesn't have a Picture");
        }

        try {
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showStats(Integer selection) {
        lblName.setText(main.getPokemon(selection).getName());
        lblType.setText(main.getPokemon(selection).getType());
        lblID.setText(Integer.toString(main.getPokemon(selection).getIdTag()));
        lblHP.setText(Integer.toString(main.getPokemon(selection).getHealth()));
        lblAtk.setText(Integer.toString(main.getPokemon(selection).getAttack()));
        lblDf.setText(Integer.toString(main.getPokemon(selection).getDefense()));
        lblSpeed.setText(Integer.toString(main.getPokemon(selection).getSpeed()));
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
