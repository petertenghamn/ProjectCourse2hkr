package scenes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import main.pokemon.Pokemon;
import main.pokemon.PokemonMapper;
import main.scenemanager.SceneManager;
import main.users.Trainer;
import main.users.User;

import java.util.ArrayList;

public class TrainerCollectionController implements Controller {

    // **************************** WORK IN PROGRESS *****************************************************

    // This scene is about:
    // the Trainer being able to see his owned pokemon "Collection"
    // Trainer being able to see his battle team "Team"
    // Trainer being able to transfer pokemon from it's collection to it's Team
    //
    // * Team can only be Max 6 Pokemons

    @FXML
    ImageView imageView;
    @FXML
    ListView<String> listCollection, listTeam;
    @FXML
    Label lblName, lblHP, lblAtk, lblDf, lblType, lblID, lblSpeed;
    private Main main;
    private ShowAllPokemonController showAll = new ShowAllPokemonController();

    @Override
    public void setMain(Main m) {
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void setUp() {
        updateListCollection();
        updateListTeam();
    }

    @Override
    public void reset() {
        updateListCollection();
        updateListTeam();

        listCollection.getSelectionModel().clearSelection();
        listTeam.getSelectionModel().clearSelection();

        Image image = new Image("scenes/view/images/pokeLogo.png");
        imageView.setImage(image);

        lblName.setText("");
        lblAtk.setText("");
        lblDf.setText("");
        lblType.setText("");
        lblID.setText("");
        lblSpeed.setText("");
        lblHP.setText("");
    }

    public void backTrainerMenu() {
        main.requestSceneChange(SceneManager.sceneName.TRAINERMENU);
    }

    public void updateListCollection() {
        User user = main.getCurrentUser();

        ArrayList<PokemonMapper> collection = ((Trainer) user).getCollection();
        ArrayList<String> pokeNicknames = new ArrayList<>();

        for (PokemonMapper pokemon : collection) {
            pokeNicknames.add(pokemon.getNickname());
        }

        // Needed for a ListView in JavaFX for some reason
        ObservableList<String> collectionPokemon = FXCollections.observableArrayList(pokeNicknames);

        listCollection.setItems(collectionPokemon);
    }

    public void showSelectedCollection() {
        User user = main.getCurrentUser();

        ArrayList<PokemonMapper> collection = ((Trainer) user).getCollection();

        for (PokemonMapper pokemon : collection) {
            if (pokemon.getNickname().equalsIgnoreCase(listCollection.getSelectionModel().getSelectedItem())) {
                imageView.setImage(main.getPokemonImage(main.getPokemonById(pokemon.getId()).getName()));

                // prints the stats to the scene
                lblName.setText(pokemon.getNickname());
                lblType.setText(main.getPokemonById(pokemon.getId()).getType());
                lblID.setText(Integer.toString(main.getPokemonById(pokemon.getId()).getIdTag()));
                lblHP.setText(Integer.toString(main.getPokemonById(pokemon.getId()).getHealth()));
                lblAtk.setText(Integer.toString(main.getPokemonById(pokemon.getId()).getAttack()));
                lblDf.setText(Integer.toString(main.getPokemonById(pokemon.getId()).getDefense()));
                lblSpeed.setText(Integer.toString(main.getPokemonById(pokemon.getId()).getSpeed()));
            }
        }
    }

    public void updateListTeam() {
        User user = main.getCurrentUser();

        ArrayList<PokemonMapper> team = ((Trainer) user).getTeam();
        ArrayList<String> pokeNicknames = new ArrayList<>();

        for (PokemonMapper pokemon : team) {
            pokeNicknames.add(pokemon.getNickname());
        }

        // Needed for a ListView in JavaFX for some reason
        ObservableList<String> collectionPokemon = FXCollections.observableArrayList(pokeNicknames);

        listTeam.setItems(collectionPokemon);
    }

    public void showSelectedTeam() {
        User user = main.getCurrentUser();

        ArrayList<PokemonMapper> team = ((Trainer) user).getTeam();

        for (PokemonMapper pokemon : team) {
            if (pokemon.getNickname().equalsIgnoreCase(listCollection.getSelectionModel().getSelectedItem())) {
                imageView.setImage(main.getPokemonImage(main.getPokemonById(pokemon.getId()).getName()));

                // prints the stats to the scene
                lblName.setText(pokemon.getNickname());
                lblType.setText(main.getPokemonById(pokemon.getId()).getType());
                lblID.setText(Integer.toString(main.getPokemonById(pokemon.getId()).getIdTag()));
                lblHP.setText(Integer.toString(main.getPokemonById(pokemon.getId()).getHealth()));
                lblAtk.setText(Integer.toString(main.getPokemonById(pokemon.getId()).getAttack()));
                lblDf.setText(Integer.toString(main.getPokemonById(pokemon.getId()).getDefense()));
                lblSpeed.setText(Integer.toString(main.getPokemonById(pokemon.getId()).getSpeed()));
            }
        }
    }

    // This adds the selected pokemon from the collection to the team
    // ** DOES NOT UPDATE THE DATABASE YET
    public void addTeam() {
        User user = main.getCurrentUser();

        ArrayList<PokemonMapper> team = ((Trainer) user).getTeam();

        if (team.size() == 6){
            System.out.println("Team size at max!");
        }
        else {
            ArrayList<PokemonMapper> collection = ((Trainer) user).getCollection();

            for (PokemonMapper mapper : collection) {
                if (mapper.getNickname().equals(listCollection.getSelectionModel().getSelectedItem())) {
                    // Makes sure that the pokemon isn't already in the team
                    boolean alreadyOnTeam = false;

                    for (PokemonMapper p : team) {
                        if (p.getNickname().equals(mapper.getNickname())) {
                            alreadyOnTeam = true;
                        }
                    }

                    if (!alreadyOnTeam) {
                        team.add(mapper);
                    } else {
                        // Add this as a Label in the Scene
                        System.out.println("You already have that pokemon in your team");
                    }
                }
            }

            ((Trainer) user).setTeam(team);
            main.updateCurrentUserDB();
            updateListTeam();
        }
    }
}
