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

import java.util.*;


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

    // -------------------------------------------------------- THIS PART ONWARDS IS ONLY TO BE USED BY THE PROFESSOR! --------------------------------------------------------
    @FXML
    TextField txtName, txtID, txtHealth, txtSpeed, txtAttack, txtDefence, txtPrice;
    @FXML
    ChoiceBox<String> choiceFirstType, choiceSecondType;
    @FXML
    Button btnUpdate;
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
    Pane paneHelp, pane3;
    // This is Used for the Search
    @FXML
    TextField txtSearch;
    private int oldID;
    private ArrayList<String> types;
    private int currency;
    private Main main;
    private Boolean help = false;

    @Override
    public void setMain(Main m) {
        //set the main so that you can call upon it to change scenes
        main = m;

        showSortedID();

        if (main.getCurrentUser() instanceof Trainer) {
            updateCurrency();
        }
    }

    @Override
    public void setUp() {
        if (main.getCurrentUser() instanceof Professor) {
            setUpProfessor();

            txtID.setText("0");
            txtName.setText("");
            txtHealth.setText("0");
            txtAttack.setText("0");
            txtDefence.setText("0");
            txtSpeed.setText("0");
            txtPrice.setText("0");

            types = new ArrayList<>();
            types.add("None");
            types.addAll(main.getTypeSelection());
            ObservableList<String> typeArr = FXCollections.observableList(types);

            choiceFirstType.setItems(typeArr);
            choiceSecondType.setItems(typeArr);

            choiceFirstType.getSelectionModel().selectFirst();
            choiceSecondType.getSelectionModel().selectFirst();
        } else if (main.getCurrentUser() instanceof Trainer) {
            setUpTrainer();
        }

        pokeBall.setVisible(false);
        btnBuy.setVisible(false);
        lblNickname.setVisible(false);
        txtNickname.setVisible(false);
        btnNoNickname.setVisible(false);
        btnNickname.setVisible(false);
        lblError.setVisible(false);
        paneHelp.setVisible(false);

        if (listView.getItems().size() > 0) {
            listView.getSelectionModel().selectFirst();
            showPokemon();
        }
    }

    private void setUpProfessor() {
        btnHelp.setVisible(false);
        lblCurrency.setVisible(false);
        lblCurrencyTitle.setVisible(false);
        pane3.setVisible(false);

        txtName.setVisible(true);
        txtID.setVisible(true);
        txtHealth.setVisible(true);
        txtSpeed.setVisible(true);
        txtAttack.setVisible(true);
        txtDefence.setVisible(true);
        txtPrice.setVisible(true);
        choiceFirstType.setVisible(true);
        choiceSecondType.setVisible(true);

        btnUpdate.setVisible(true);

        lblName.setVisible(false);
        lblID.setVisible(false);
        lblType.setVisible(false);
        lblHP.setVisible(false);
        lblAtk.setVisible(false);
        lblDf.setVisible(false);
        lblSpeed.setVisible(false);
        lblPrice.setVisible(false);
    }

    private void setUpTrainer() {
        btnHelp.setVisible(true);
        lblCurrencyTitle.setVisible(true);
        lblCurrency.setVisible(true);
        pane3.setVisible(true);

        txtName.setVisible(false);
        txtID.setVisible(false);
        txtHealth.setVisible(false);
        txtSpeed.setVisible(false);
        txtAttack.setVisible(false);
        txtDefence.setVisible(false);
        txtPrice.setVisible(false);
        choiceFirstType.setVisible(false);
        choiceSecondType.setVisible(false);

        btnUpdate.setVisible(false);

        lblName.setVisible(true);
        lblID.setVisible(true);
        lblType.setVisible(true);
        lblHP.setVisible(true);
        lblAtk.setVisible(true);
        lblDf.setVisible(true);
        lblSpeed.setVisible(true);
        lblPrice.setVisible(true);
    }

    @Override
    public void reset() {
        listView.getSelectionModel().clearSelection();

        Image image = new Image("scenes/view/images/pokeLogo.png");
        imageView.setImage(image);

        //Trainer
        lblName.setText("0");
        lblAtk.setText("0");
        lblDf.setText("0");
        lblType.setText("0");
        lblID.setText("0");
        lblSpeed.setText("0");
        lblHP.setText("0");

        //Professor
        txtID.setText("0");
        txtName.setText("");
        txtHealth.setText("0");
        txtAttack.setText("0");
        txtDefence.setText("0");
        txtSpeed.setText("0");
        txtPrice.setText("0");
        choiceFirstType.getSelectionModel().selectFirst();
        choiceSecondType.getSelectionModel().selectFirst();

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

        txtSearch.clear();
    }

    private void updateCurrency() {
        User user = main.getCurrentUser();

        if (user instanceof Trainer) {
            currency = ((Trainer) user).getCurrency();
        }

        String currencyString = ((Integer) currency).toString();

        lblCurrency.setText(currencyString);

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

    public void updatePokemon() {
        //tell the main to update the pokemon selected information
        if (oldID == 0) {
            System.out.println("Please select a pokemon to edit first!");
        } else if (choiceFirstType.getValue().equals("None")) {
            System.out.println("First type cannot be None. Please select a first type!");
        }
        //check to see the values are within bounds
        else if (txtName.getText().isEmpty() ||
                Integer.parseInt(txtID.getText()) <= 0 || Integer.parseInt(txtID.getText()) >= 1000 ||
                Integer.parseInt(txtHealth.getText()) <= 0 || Integer.parseInt(txtHealth.getText()) >= 1000 ||
                Integer.parseInt(txtAttack.getText()) <= 0 || Integer.parseInt(txtAttack.getText()) >= 1000 ||
                Integer.parseInt(txtDefence.getText()) <= 0 || Integer.parseInt(txtDefence.getText()) >= 1000 ||
                Integer.parseInt(txtSpeed.getText()) <= 0 || Integer.parseInt(txtSpeed.getText()) >= 1000 ||
                Integer.parseInt(txtPrice.getText()) <= 0 || Integer.parseInt(txtPrice.getText()) >= 1000) {
            if (txtName.getText().isEmpty()) {
                System.out.println("Name cannot be empty!");
            } else if (Integer.parseInt(txtID.getText()) <= 0 || Integer.parseInt(txtID.getText()) >= 1000) {
                System.out.println("ID needs to be between 1-999");
            } else if (Integer.parseInt(txtHealth.getText()) <= 0 || Integer.parseInt(txtHealth.getText()) >= 1000) {
                System.out.println("Health needs to be between 1-999");
            } else if (Integer.parseInt(txtAttack.getText()) <= 0 || Integer.parseInt(txtAttack.getText()) >= 1000) {
                System.out.println("Attack needs to be between 1-999");
            } else if (Integer.parseInt(txtDefence.getText()) <= 0 || Integer.parseInt(txtDefence.getText()) >= 1000) {
                System.out.println("Defence needs to be between 1-999");
            } else if (Integer.parseInt(txtSpeed.getText()) <= 0 || Integer.parseInt(txtSpeed.getText()) >= 1000) {
                System.out.println("Speed needs to be between 1-999");
            } else if (Integer.parseInt(txtPrice.getText()) <= 0 || Integer.parseInt(txtPrice.getText()) >= 1000) {
                System.out.println("Price needs to be between 1-999");
            }
        } else {
            Pokemon edit = new Pokemon(Integer.parseInt(txtID.getText()), txtName.getText(), Integer.parseInt(txtHealth.getText()),
                    Integer.parseInt(txtAttack.getText()), Integer.parseInt(txtDefence.getText()), Integer.parseInt(txtSpeed.getText()), Integer.parseInt(txtPrice.getText()),
                    (choiceFirstType.getValue()) + ((choiceSecondType.getValue().equals("None")) ? "" : " and " + choiceSecondType.getValue()));
            if (!main.editPokemon(oldID, edit)) {
                System.out.println("Error: Possibly conflicts with another pokemon's ID");
            }
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

        //for professor editing
        oldID = main.getPokemonByName(selection).getIdTag();

        txtName.setText(main.getPokemonByName(selection).getName());
        txtID.setText(Integer.toString(main.getPokemonByName(selection).getIdTag()));
        txtHealth.setText(Integer.toString(main.getPokemonByName(selection).getHealth()));
        txtAttack.setText(Integer.toString(main.getPokemonByName(selection).getAttack()));
        txtDefence.setText(Integer.toString(main.getPokemonByName(selection).getDefense()));
        txtSpeed.setText(Integer.toString(main.getPokemonByName(selection).getSpeed()));
        txtPrice.setText(Integer.toString(main.getPokemonByName(selection).getCost()));
        showTypeSelection(main.getPokemonByName(selection));
    }

    private void showTypeSelection(Pokemon pokemon) {
        //split up the string type of the pokemon to display on two options
        ObservableList<String> types = choiceFirstType.getItems();

        if (pokemon.getType().contains("and")) {
            String[] strings = pokemon.getType().split(" and ");
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).equalsIgnoreCase(strings[0])) {
                    choiceFirstType.getSelectionModel().select(i);
                } else if (types.get(i).equalsIgnoreCase(strings[1])) {
                    choiceSecondType.getSelectionModel().select(i);
                }
            }
        } else {
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).equalsIgnoreCase(pokemon.getType())) {
                    choiceFirstType.getSelectionModel().select(i);
                }
            }
            choiceSecondType.getSelectionModel().selectFirst();
        }


        /*
        if (pokemon.getType().contains("and")){
            //group by their first type listed
            String parse = pokemon.getType().substring(0, pokemon.getType().indexOf(" "));
            if (!typeMap.containsKey(parse)){
                typeMap.put(parse, new ArrayList<>());
                typeMap.get(parse).add(pokemon.getName());
            }
            else
            {
                typeMap.get(parse).add(pokemon.getName());
            }
        }
        */
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
                if (main.acquirePokemon(pokemon.getIdTag(), txtNickname.getText(), true)) {
                    catchPokemon();
                    lblError.setVisible(false);
                    updateCurrency();
                }
            }
        }
    }

    public void pokemonNoNickname() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();

        for (Pokemon pokemon : allPokemon) {
            if (pokemon.getName().equalsIgnoreCase(listView.getSelectionModel().getSelectedItem())) {
                if (main.acquirePokemon(pokemon.getIdTag(), null, true)) {
                    catchPokemon();
                    lblError.setVisible(false);
                    updateCurrency();
                }
            }
        }
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

    // All the following methods are used by the search Function
    public void search() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();
        String search = txtSearch.getText();

        // The Name of the Pokemon should be returned to the ListView
        ArrayList<String> searchResults = new ArrayList<>();

        // The following searches should be future proof
        // Search for certain types
        for (Pokemon pokemon : allPokemon) {
            if (search.contains(pokemon.getType())) {
                searchResults.add(pokemon.getName());
            }
            // Using Type: Modifier sorts all Pokemon by type
            else if (search.contains("Type") || search.contains("type")) {
                showSortedType();
                return; // Return Needs to be there or else the program shows no results! // The return just exits this method
            }
        }

        // Search for a specific Pokemon
        for (Pokemon pokemon : allPokemon) {
            if (search.equalsIgnoreCase(pokemon.getName())) {
                searchResults.add(pokemon.getName());
            }
            // Using Name: modifier sorts all Pokemon by Name
            else if (search.contains("Name") || search.contains("name")) {
                showSortedName();
                return; // Return Needs to be there or else the program shows no results! // The return just exits this method
            }
        }

        // Specify the Search to:
        if (search.contains("ID:") || search.contains("id:")) {
            // Search for Pokemon by ID
            for (Pokemon pokemon : allPokemon) {
                // ID the Same as:
                if (search.contains("=")) {
                    //get the value entered to compare with
                    String numericPart = search.substring(search.indexOf("=") + 1);
                    int number = Integer.parseInt(numericPart);
                    if (pokemon.getIdTag() == number) {
                        searchResults.add(pokemon.getName());
                    }
                }
                // ID is less than:
                else if (search.contains(">")) {
                    //get the value entered to compare with
                    String numericPart = search.substring(search.indexOf(">") + 1);
                    int number = Integer.parseInt(numericPart);
                    if (pokemon.getIdTag() >= number) {
                        searchResults.add(pokemon.getName());
                    }
                }
                // ID is greater than:
                else if (search.contains("<")) {
                    //get the value entered to compare with
                    String numericPart = search.substring(search.indexOf("<") + 1);
                    int number = Integer.parseInt(numericPart);
                    if (pokemon.getIdTag() <= number) {
                        searchResults.add(pokemon.getName());
                    }
                }
                // IF no search modifier then the program just sorts by ID
                else {
                    showSortedID();
                    return; // Return Needs to be there or else the program shows no results! // The return just exits this method
                }
            }
        }

        // Specify the Search to:
        if (search.contains("HP:") || search.contains("hp:") || search.contains("Health:") || search.contains("health:")) {
            // Search for Pokemon by HP
            for (Pokemon pokemon : allPokemon) {
                // Higher HP Than:
                if (search.contains(">")) {
                    //get the value entered to compare with
                    String numericPart = search.substring(search.indexOf(">") + 1);
                    int number = Integer.parseInt(numericPart);
                    if (pokemon.getHealth() >= number) {
                        searchResults.add(pokemon.getName());
                    }
                }
                // Less HP Than:
                else if (search.contains("<")) {
                    //get the value entered to compare with
                    String numericPart = search.substring(search.indexOf("<") + 1);
                    int number = Integer.parseInt(numericPart);
                    if (pokemon.getHealth() <= number) {
                        searchResults.add(pokemon.getName());
                    }
                }
                // Equal HP To:
                else if (search.contains("=")) {
                    //get the value entered to compare with
                    String numericPart = search.substring(search.indexOf("=") + 1);
                    int number = Integer.parseInt(numericPart);
                    if (pokemon.getHealth() == number) {
                        searchResults.add(pokemon.getName());
                    }
                }
                // Sort All pokemon by HP
                else {
                    showSortedHP();
                    return; // Return Needs to be there or else the program shows no results! // The return just exits this method
                }
            }
        }


        // IF the results are empty tells the user
        if (searchResults.isEmpty()) {
            searchResults.add("There are no results!");
            searchResults.add("");
            searchResults.add("Make sure your spelling is correct");
            searchResults.add("Or try searching for something else");
            searchResults.add("");
            searchResults.add("Tip: ");
            searchResults.add("You can search for specific stats");
            searchResults.add("by typing for ex) ID: = 5 or HP: > 50");
            searchResults.add("Make sure there is space after modifiers");
        }

        ObservableList<String> observableResults = FXCollections.observableArrayList(searchResults);
        listView.setItems(observableResults);
    }

    private void showSortedType() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();
        Map<String, ArrayList<String>> typeMap = new TreeMap<>();

        //creates a hashmap of <type, pokemonNameArray> to group all pokemon accordingly
        for (Pokemon pokemon : allPokemon) {
            if (!typeMap.containsKey(pokemon.getType())) {
                if (pokemon.getType().contains("and")) {
                    //group by their first type listed
                    String parse = pokemon.getType().substring(0, pokemon.getType().indexOf(" "));
                    if (!typeMap.containsKey(parse)) {
                        typeMap.put(parse, new ArrayList<>());
                        typeMap.get(parse).add(pokemon.getName());
                    } else {
                        typeMap.get(parse).add(pokemon.getName());
                    }
                } else {
                    typeMap.put(pokemon.getType(), new ArrayList<>());
                    typeMap.get(pokemon.getType()).add(pokemon.getName());
                }
            } else {
                typeMap.get(pokemon.getType()).add(pokemon.getName());
            }
        }

        ArrayList<String> pokemonNames = new ArrayList<>();

        for (ArrayList<String> array : typeMap.values()) {
            pokemonNames.addAll(array);
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);

        listView.setItems(observableListPokemons);
    }

    private void showSortedID() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();
        ArrayList<String> pokemonNames = new ArrayList<>();

        for (Pokemon pokemon : allPokemon) {
            pokemonNames.add(pokemon.getName());
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);
        listView.setItems(observableListPokemons);
    }

    private void showSortedName() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();

        ArrayList<String> pokemonNames = new ArrayList<>();

        for (Pokemon pokemon : allPokemon) {
            pokemonNames.add(pokemon.getName());
        }

        Collections.sort(pokemonNames);

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);

        listView.setItems(observableListPokemons);
    }

    private void showSortedHP() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();
        ArrayList<String> pokemonNames = new ArrayList<>();

        for (Pokemon pokemon : allPokemon) {
            for (Pokemon higherHP : allPokemon) {
                if (higherHP.getHealth() > pokemon.getHealth()) {
                    pokemonNames.add(higherHP.getName());
                }
            }
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);

        listView.setItems(observableListPokemons);
    }
}

