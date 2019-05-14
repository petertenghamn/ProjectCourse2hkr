package scenes.controller;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import main.Main;
import main.pokemon.Pokemon;
import main.scenemanager.SceneManager;
import main.users.Professor;
import main.users.Trainer;
import main.users.User;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ShowAllPokemonController implements Controller {

    // This Scene will be used by both the professor and the trainer in order to show all the Pokemon in the Database
    // The trainer will be able to then be able to see the "buy" button while the professor can't

    private Main main;
    private Boolean canBuy = true;

    @Override
    public void setMain(Main m) {
        //set the main so that you can call upon it to change scenes
        main = m;

        showPokemonCollection();
    }

    @Override
    public void setUp() {
        if (main.getCurrentUser() instanceof Professor) {
            canBuy = false;
        } else if (main.getCurrentUser() instanceof Trainer) {
            canBuy = true;
        }

        pokeBall.setVisible(false);
        btnBuy.setVisible(false);
        lblNickname.setVisible(false);
        txtNickname.setVisible(false);
        btnNoNickname.setVisible(false);
        btnNickname.setVisible(false);
    }

    @Override
    public void reset() {
        listView.getSelectionModel().clearSelection();

        Image image = new Image("scenes/view/images/pokeLogo.png");
        imageView.setImage(image);

        lblName.setText("");
        lblAtk.setText("");
        lblDf.setText("");
        lblType.setText("");
        lblID.setText("");
        lblSpeed.setText("");
        lblHP.setText("");

        lblNickname.setVisible(false);
        txtNickname.setVisible(false);
        btnNoNickname.setVisible(false);
        btnNickname.setVisible(false);
        btnBuy.setVisible(false);

        txtNickname.clear();

        pokeBall.setVisible(false);
    }

    @FXML
    Button btnBuy;

    @FXML
    ListView<String> listView;

    @FXML
    Label lblName, lblID, lblType, lblHP, lblAtk, lblDf, lblSpeed;

    @FXML
    ImageView imageView;

    private void showPokemonCollection() {
        Pokemon[] allPokemon = main.getAllPokemon();
        ArrayList<String> pokemonNames = new ArrayList<>();

        for (Pokemon pokemon : allPokemon) {
            pokemonNames.add(pokemon.getName());
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);
        listView.setItems(observableListPokemons);
    }

    public void showPokemon() {
        btnBuy.setVisible(true);

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

    // -------------------------------------------------------- THIS PART ONWARDS IS ONLY TO BE USED BY THE TRAINER! --------------------------------------------------------
    // ---------------------------------------- NICKNAME SUBSCENE CODE STARTS HERE ----------------------------------------
    @FXML
    Label lblNickname;

    @FXML
    TextField txtNickname;

    @FXML
    Button btnNoNickname, btnNickname, btnSelect;

    @FXML
    Circle pokeBall;

    public void reArrangeScene() {
        lblNickname.setText("Give your new friend a nickname!");
        lblNickname.setVisible(true);
        txtNickname.setVisible(true);
        btnNoNickname.setVisible(true);
        btnNickname.setVisible(true);
    }

    // Once it has been named use this method to store it
    public void pokemonNickname() {
        Pokemon[] allPokemon = main.getAllPokemon();

        for (Pokemon pokemon : allPokemon) {
            if (pokemon.getName().equalsIgnoreCase(listView.getSelectionModel().getSelectedItem())) {
                main.acquirePokemon(pokemon.getIdTag(), txtNickname.getText());
            }
        }

        catchPokemon();
    }

    public void pokemonNoNickname(){
        Pokemon[] allPokemon = main.getAllPokemon();

        for (Pokemon pokemon : allPokemon) {
            if (pokemon.getName().equalsIgnoreCase(listView.getSelectionModel().getSelectedItem())) {
                main.acquirePokemon(pokemon.getIdTag(), null);
            }
        }

        catchPokemon();
    }

    // Adds an animation of a pokeball to catch the pokemon
    private void catchPokemon(){
        pokeBall.setVisible(true);
        btnNickname.setVisible(false);
        btnNoNickname.setVisible(false);

        RotateTransition animation = new RotateTransition(Duration.millis(750), pokeBall);

        animation.setFromAngle(0);
        animation.setToAngle(25);
        animation.setCycleCount(5);
        animation.setAutoReverse(true);
        animation.play();

        lblNickname.setText("Pokemon Caught!");

        imageView.setVisible(false);
        btnBuy.setVisible(false);
        btnSelect.setVisible(false);
    }
}
