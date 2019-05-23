package scenes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.Main;
import main.pokemon.Pokemon;
import main.scenemanager.SceneManager;
import main.users.Professor;
import main.users.Trainer;
import main.users.User;

import java.util.ArrayList;
import java.util.Collections;


public class ShowAllPokemonController implements Controller {

    // This Scene will be used by both the professor and the trainer in order to show all the Pokemon in the Database
    // The trainer will be able to then be able to see the "buy" button while the professor can't

    @FXML
    Button btnBuy;
    @FXML
    ListView<String> listView;
    @FXML
    Label lblName, lblID, lblType, lblHP, lblAtk, lblDf, lblSpeed, lblPrice;
    @FXML
    ImageView imageView;
    @FXML
    ChoiceBox<String> filterType;
    // -------------------------------------------------------- THIS PART ONWARDS IS ONLY TO BE USED BY THE TRAINER! --------------------------------------------------------
    // ---------------------------------------- NICKNAME SUBSCENE CODE STARTS HERE ----------------------------------------
    @FXML
    Label lblNickname, lblError, lblCurrency, lblCurrencyTitle;
    @FXML
    TextField txtNickname;
    @FXML
    Button btnNoNickname, btnNickname, btnSelect, btnGetNewPokemon, btnHelp;
    @FXML
    ImageView pokeBall;
    @FXML
    Pane paneHelp;

    private int currency;
    private Main main;
    private Boolean canBuy = true;
    private Boolean help = false;

    @Override
    public void setMain(Main m) {
        //set the main so that you can call upon it to change scenes
        main = m;

        showFilteredID();

        if (main.getCurrentUser() instanceof Trainer) {
            updateCurrency();
        }
    }

    @Override
    public void setUp() {
        if (main.getCurrentUser() instanceof Professor) {
            canBuy = false;
            btnHelp.setVisible(false);
            lblCurrency.setVisible(false);
            lblCurrencyTitle.setVisible(false);
        } else if (main.getCurrentUser() instanceof Trainer) {
            canBuy = true;
            btnHelp.setVisible(true);
            lblCurrencyTitle.setVisible(true);
            lblCurrency.setVisible(true);
        }

        pokeBall.setVisible(false);
        btnBuy.setVisible(false);
        lblNickname.setVisible(false);
        txtNickname.setVisible(false);
        btnNoNickname.setVisible(false);
        btnNickname.setVisible(false);
        lblError.setVisible(false);

        ArrayList<String> filterTypes = new ArrayList<>();

        filterTypes.add("ID #");
        filterTypes.add("Name");
        filterTypes.add("Type");

        ObservableList<String> observableList = FXCollections.observableArrayList(filterTypes);

        filterType.setItems(observableList);
    }

    @Override
    public void reset() {
        listView.getSelectionModel().clearSelection();

        Image image = new Image("scenes/view/images/pokeLogo.png");
        imageView.setImage(image);

        lblName.setText("0");
        lblAtk.setText("0");
        lblDf.setText("0");
        lblType.setText("0");
        lblID.setText("0");
        lblSpeed.setText("0");
        lblHP.setText("0");

        lblNickname.setVisible(false);
        txtNickname.setVisible(false);
        btnNoNickname.setVisible(false);
        btnNickname.setVisible(false);
        btnBuy.setVisible(false);

        txtNickname.clear();

        btnSelect.setVisible(true);
        btnGetNewPokemon.setVisible(false);
        imageView.setVisible(true);
        pokeBall.setVisible(false);
        lblError.setVisible(false);
    }

    private void updateCurrency() {
        User user = main.getCurrentUser();

        if (user instanceof Trainer) {
            currency = ((Trainer) user).getCurrency();
        }

        String currencyString = ((Integer) currency).toString();

        lblCurrency.setText(currencyString);

        filterType.getSelectionModel().clearAndSelect(0);
    }

    public void showPokemon() {
        if (main.getCurrentUser() instanceof Professor) {
            btnBuy.setVisible(false);
        } else if (main.getCurrentUser() instanceof Trainer) {
            btnBuy.setVisible(true);
        }

        lblError.setVisible(false);

        if (listView.getSelectionModel().getSelectedItem() != null) {
            showStats(listView.getSelectionModel().getSelectedItem());
            showImage(listView.getSelectionModel().getSelectedItem());
        } else {
            System.out.println("You need to select something first!");
        }

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
        lblPrice.setText(Integer.toString(main.getPokemonByName(selection).getCost()));
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

    public void reArrangeScene() {
        lblNickname.setText("Give your new friend a nickname!");
        lblNickname.setVisible(true);
        txtNickname.setVisible(true);
        btnNoNickname.setVisible(true);
        btnNickname.setVisible(true);
    }

    // Once it has been named use this method to store it
    public void pokemonNickname() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();

        for (Pokemon pokemon : allPokemon) {
            if (txtNickname.getText().isEmpty()) {
                lblError.setVisible(true);
                lblError.setText("Nickname is empty!");
            } else if (pokemon.getName().equalsIgnoreCase(listView.getSelectionModel().getSelectedItem())) {
                main.acquirePokemon(pokemon.getIdTag(), txtNickname.getText(), true);
            } else {
                catchPokemon();
                lblError.setVisible(false);
            }
        }
    }

    public void pokemonNoNickname() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();

        for (Pokemon pokemon : allPokemon) {
            if (pokemon.getName().equalsIgnoreCase(listView.getSelectionModel().getSelectedItem())) {
                main.acquirePokemon(pokemon.getIdTag(), null, true);
            }
        }
        catchPokemon();
        lblError.setVisible(false);
    }

    private void catchPokemon() {
        pokeBall.setVisible(true);
        btnNickname.setVisible(false);
        btnNoNickname.setVisible(false);

        lblNickname.setText("Pokemon caught!");

        txtNickname.setVisible(false);
        imageView.setVisible(false);
        btnBuy.setVisible(false);
        btnSelect.setVisible(false);
        btnGetNewPokemon.setVisible(true);
    }

    public void getNewPokemon() {
        pokeBall.setVisible(false);
        btnNickname.setVisible(false);
        btnNoNickname.setVisible(false);

        lblNickname.setText("Pokemon caught!");

        imageView.setVisible(true);
        btnBuy.setVisible(true);
        btnSelect.setVisible(true);
        lblNickname.setVisible(false);
        txtNickname.setVisible(false);
        txtNickname.clear();
        btnGetNewPokemon.setVisible(false);
    }

    public void showHelp() {

        if (help) {
            paneHelp.setVisible(false);
            help = false;
        } else {
            paneHelp.setVisible(true);
            help = true;
        }
    }

    public void changeFilter() {
        String selection = filterType.getSelectionModel().getSelectedItem();

        if (selection.equalsIgnoreCase("ID #")){
            showFilteredID();
        }else if (selection.equalsIgnoreCase("Name")){
            showFilteredName();
        }else if (selection.equalsIgnoreCase("Type")){
            showFilteredType();
        }
    }

    private void showFilteredName() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();

        ArrayList<String> pokemonNames = new ArrayList<>();

        for (Pokemon pokemon : allPokemon) {
            pokemonNames.add(pokemon.getName());
        }

        Collections.sort(pokemonNames);

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);

        listView.setItems(observableListPokemons);

    }

    private void showFilteredType() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();

        ArrayList<String> pokemonFire = new ArrayList<>();
        ArrayList<String> pokemonWater = new ArrayList<>();
        ArrayList<String> pokemonGrass = new ArrayList<>();
        ArrayList<String> pokemonElectric = new ArrayList<>();

        for (Pokemon pokemon : allPokemon) {
            if (pokemon.getType().contains("Fire")) {
                pokemonFire.add(pokemon.getName());
            } else if (pokemon.getType().contains("Water")) {
                pokemonWater.add(pokemon.getName());
            } else if (pokemon.getType().contains("Grass")) {
                pokemonGrass.add(pokemon.getName());
            } else if (pokemon.getType().contains("Electric")) {
                pokemonElectric.add(pokemon.getName());
            } else {
                System.out.println("Error Assigning Pokemon " + pokemon.getName() + "'s Type");
            }
        }

        ArrayList<String> pokemonNames = new ArrayList<>();
        pokemonNames.addAll(pokemonFire);
        pokemonNames.addAll(pokemonWater);
        pokemonNames.addAll(pokemonGrass);
        pokemonNames.addAll(pokemonElectric);

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);

        listView.setItems(observableListPokemons);
    }

    private void showFilteredID() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();
        ArrayList<String> pokemonNames = new ArrayList<>();

        for (Pokemon pokemon : allPokemon) {
            pokemonNames.add(pokemon.getName());
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);
        listView.setItems(observableListPokemons);
    }
}

