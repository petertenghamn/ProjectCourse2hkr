package scenes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    Button btnNoNickname, btnNickname, btnGetNewPokemon, btnHelp;
    @FXML
    ImageView pokeBall;
    @FXML
    Pane paneHelp, pane3;
    // This is Used for the Search
    @FXML
    TextField txtSearchValue;
    @FXML
    ChoiceBox<String> choiceSearch, choiceSearchSymbol;
    private int oldID;
    private int currency;
    private Main main;
    private boolean help = false, searchSetup = false;

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

            ArrayList<String> types = new ArrayList<>();
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

        //setup search bar properties
        if (!searchSetup){ setUpSearch(); }
        choiceSearch.getSelectionModel().selectFirst();
        choiceSearchSymbol.getSelectionModel().selectFirst();
        txtSearchValue.setText("0");
        choiceSearchSymbol.setDisable(true);
        txtSearchValue.setDisable(true);

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

    private void setUpSearch(){
        ArrayList<String> searchTerms = new ArrayList<>();
        searchTerms.add("ID");
        searchTerms.add("Type");
        searchTerms.add("Name");
        searchTerms.add("Health");
        searchTerms.add("Attack");
        searchTerms.add("Defense");
        searchTerms.add("Speed");
        searchTerms.add("Price");
        ObservableList<String> arr = FXCollections.observableArrayList(searchTerms);
        choiceSearch.setItems(arr);

        ArrayList<String> searchSymbols = new ArrayList<>();
        searchSymbols.add("None");
        searchSymbols.add("=");
        searchSymbols.add("<");
        searchSymbols.add(">");
        arr = FXCollections.observableArrayList(searchSymbols);
        choiceSearchSymbol.setItems(arr);

        choiceSearch.setOnAction(this::searchChoiceChanged);
        choiceSearchSymbol.setOnAction(this::searchChoiceSymbolChanged);

        searchSetup = true;
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

        btnGetNewPokemon.setVisible(false);
        imageView.setVisible(true);
        pokeBall.setVisible(false);
        lblError.setVisible(false);

        choiceSearch.getSelectionModel().selectFirst();
        choiceSearchSymbol.getSelectionModel().selectFirst();
        txtSearchValue.setText("0");
        choiceSearchSymbol.setDisable(true);
        txtSearchValue.setDisable(true);
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
    // This won't work if the pokemon stats change by level and not by evolution!
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
        btnGetNewPokemon.setVisible(true);
    }

    public void getNewPokemon() {
        pokeBall.setVisible(false);
        btnNickname.setVisible(false);
        btnNoNickname.setVisible(false);

        lblNickname.setText("Pokemon caught!");

        imageView.setVisible(true);
        btnBuy.setVisible(true);
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

    //enable options according to the option selected
    @FXML
    private void searchChoiceChanged(ActionEvent event) {
        try {
            if (choiceSearch.getValue().equalsIgnoreCase("health") ||
                    choiceSearch.getValue().equalsIgnoreCase("attack") ||
                    choiceSearch.getValue().equalsIgnoreCase("defense") ||
                    choiceSearch.getValue().equalsIgnoreCase("speed") ||
                    choiceSearch.getValue().equalsIgnoreCase("price")) {
                choiceSearchSymbol.setDisable(false);
            } else {
                choiceSearchSymbol.setDisable(true);
            }
        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    //enable the text field if viable option selected
    @FXML
    private void searchChoiceSymbolChanged(ActionEvent event) {
        try {
            if (!choiceSearchSymbol.getValue().equalsIgnoreCase("none")) {
                txtSearchValue.setDisable(false);
            } else {
                txtSearchValue.setDisable(true);
            }
        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    // All the following methods are used by the search Function
    public void search() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();

        // The Name of the Pokemon should be returned to the ListView
        ArrayList<String> searchResults = new ArrayList<>();

        if (choiceSearch.getValue().equalsIgnoreCase("id")){
            showSortedID();
            return;
        }
        else if (choiceSearch.getValue().equalsIgnoreCase("type")){
            showSortedType();
            return;
        }
        else if (choiceSearch.getValue().equalsIgnoreCase("name")){
            showSortedName();
            return;
        }
        else if (choiceSearch.getValue().equalsIgnoreCase("health")) {
            if (choiceSearchSymbol.getValue().equalsIgnoreCase("none")) {
                //sort according to order of selected value
                showSortedHP();
                return;
            } else {
                try {
                    //check that the value entered will include a result
                    int lowest = allPokemon.get(0).getHealth(), highest = allPokemon.get(0).getHealth();
                    for (Pokemon p : allPokemon){
                        if (p.getHealth() < lowest){
                            lowest = p.getHealth();
                        }
                        if (p.getHealth() > highest){
                            highest = p.getHealth();
                        }
                    }
                    int number = Integer.parseInt(txtSearchValue.getText());
                    if (number < lowest){
                        number = lowest;
                        txtSearchValue.setText(Integer.toString(lowest));
                    } else if (number > highest){
                        number = highest;
                        txtSearchValue.setText(Integer.toString(highest));
                    }
                    //check for results within given bounds
                    if (choiceSearchSymbol.getValue().equalsIgnoreCase("=")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getHealth() == number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                    else if (choiceSearchSymbol.getValue().equalsIgnoreCase("<")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getHealth() <= number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                    else if (choiceSearchSymbol.getValue().equalsIgnoreCase(">")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getHealth() >= number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Input value needs to be an int");
                }
            }
        }
        else if (choiceSearch.getValue().equalsIgnoreCase("attack")){
            if (choiceSearchSymbol.getValue().equalsIgnoreCase("none")) {
                //sort according to order of selected value
                showSortedATK();
                return;
            } else {
                try {
                    //check that the value entered will include a result
                    int lowest = allPokemon.get(0).getAttack(), highest = allPokemon.get(0).getAttack();
                    for (Pokemon p : allPokemon){
                        if (p.getAttack() < lowest){
                            lowest = p.getAttack();
                        }
                        if (p.getAttack() > highest){
                            highest = p.getAttack();
                        }
                    }
                    int number = Integer.parseInt(txtSearchValue.getText());
                    if (number < lowest){
                        number = lowest;
                        txtSearchValue.setText(Integer.toString(lowest));
                    } else if (number > highest){
                        number = highest;
                        txtSearchValue.setText(Integer.toString(highest));
                    }
                    //check for results within given bounds
                    if (choiceSearchSymbol.getValue().equalsIgnoreCase("=")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getAttack() == number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                    else if (choiceSearchSymbol.getValue().equalsIgnoreCase("<")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getAttack() <= number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                    else if (choiceSearchSymbol.getValue().equalsIgnoreCase(">")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getAttack() >= number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Input value needs to be an int");
                }
            }
        }
        else if (choiceSearch.getValue().equalsIgnoreCase("defense")){
            if (choiceSearchSymbol.getValue().equalsIgnoreCase("none")) {
                //sort according to order of selected value
                showSortedDEF();
                return;
            } else {
                try {
                    //check that the value entered will include a result
                    int lowest = allPokemon.get(0).getDefense(), highest = allPokemon.get(0).getDefense();
                    for (Pokemon p : allPokemon){
                        if (p.getDefense() < lowest){
                            lowest = p.getDefense();
                        }
                        if (p.getAttack() > highest){
                            highest = p.getDefense();
                        }
                    }
                    int number = Integer.parseInt(txtSearchValue.getText());
                    if (number < lowest){
                        number = lowest;
                        txtSearchValue.setText(Integer.toString(lowest));
                    } else if (number > highest){
                        number = highest;
                        txtSearchValue.setText(Integer.toString(highest));
                    }
                    //check for results within given bounds
                    if (choiceSearchSymbol.getValue().equalsIgnoreCase("=")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getDefense() == number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                    else if (choiceSearchSymbol.getValue().equalsIgnoreCase("<")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getDefense() <= number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                    else if (choiceSearchSymbol.getValue().equalsIgnoreCase(">")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getDefense() >= number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Input value needs to be an int");
                }
            }
        }
        else if (choiceSearch.getValue().equalsIgnoreCase("speed")){
            if (choiceSearchSymbol.getValue().equalsIgnoreCase("none")) {
                //sort according to order of selected value
                showSortedSPD();
                return;
            } else {
                try {
                    //check that the value entered will include a result
                    int lowest = allPokemon.get(0).getSpeed(), highest = allPokemon.get(0).getSpeed();
                    for (Pokemon p : allPokemon){
                        if (p.getSpeed() < lowest){
                            lowest = p.getSpeed();
                        }
                        if (p.getSpeed() > highest){
                            highest = p.getSpeed();
                        }
                    }
                    int number = Integer.parseInt(txtSearchValue.getText());
                    if (number < lowest){
                        number = lowest;
                        txtSearchValue.setText(Integer.toString(lowest));
                    } else if (number > highest){
                        number = highest;
                        txtSearchValue.setText(Integer.toString(highest));
                    }
                    //check for results within given bounds
                    if (choiceSearchSymbol.getValue().equalsIgnoreCase("=")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getSpeed() == number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                    else if (choiceSearchSymbol.getValue().equalsIgnoreCase("<")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getSpeed() <= number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                    else if (choiceSearchSymbol.getValue().equalsIgnoreCase(">")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getSpeed() >= number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Input value needs to be an int");
                }
            }
        }
        else if (choiceSearch.getValue().equalsIgnoreCase("price")){
            if (choiceSearchSymbol.getValue().equalsIgnoreCase("none")) {
                //sort according to order of selected value
                showSortedPrice();
                return;
            } else {
                try {
                    //check that the value entered will include a result
                    int lowest = allPokemon.get(0).getCost(), highest = allPokemon.get(0).getCost();
                    for (Pokemon p : allPokemon){
                        if (p.getCost() < lowest){
                            lowest = p.getCost();
                        }
                        if (p.getCost() > highest){
                            highest = p.getCost();
                        }
                    }
                    int number = Integer.parseInt(txtSearchValue.getText());
                    if (number < lowest){
                        number = lowest;
                        txtSearchValue.setText(Integer.toString(lowest));
                    } else if (number > highest){
                        number = highest;
                        txtSearchValue.setText(Integer.toString(highest));
                    }
                    //check for results within given bounds
                    if (choiceSearchSymbol.getValue().equalsIgnoreCase("=")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getCost() == number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                    else if (choiceSearchSymbol.getValue().equalsIgnoreCase("<")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getCost() <= number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                    else if (choiceSearchSymbol.getValue().equalsIgnoreCase(">")) {
                        for (Pokemon pokemon : allPokemon) {
                            if (pokemon.getCost() >= number) {
                                searchResults.add(pokemon.getName());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Input value needs to be an int");
                }
            }
        }

        // IF the results are empty tells the user
        if (searchResults.isEmpty()) {
            searchResults.add("There are no results!");
            searchResults.add("");
            searchResults.add("Make sure the value you entered");
            searchResults.add("contains only numbers,");
            searchResults.add("no letters or symbols");
            searchResults.add("");
            searchResults.add("Tip: make sure the value is a");
            searchResults.add("reasonable value to get results");
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

    //This is fine... no worries... it works :D
    @SuppressWarnings("unchecked")
    private void showSortedHP() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();
        ArrayList<String> pokemonNames = new ArrayList<>();

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Pokemon p : allPokemon){
            map.put(p.getName(), p.getHealth());
        }
        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        for (Object e : a) {
            pokemonNames.add(((Map.Entry<String, Integer>) e).getKey());
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);

        listView.setItems(observableListPokemons);
    }

    //This is fine... no worries... it works :D
    @SuppressWarnings("unchecked")
    private void showSortedATK() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();
        ArrayList<String> pokemonNames = new ArrayList<>();

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Pokemon p : allPokemon){
            map.put(p.getName(), p.getAttack());
        }
        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        for (Object e : a) {
            pokemonNames.add(((Map.Entry<String, Integer>) e).getKey());
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);

        listView.setItems(observableListPokemons);
    }

    //This is fine... no worries... it works :D
    @SuppressWarnings("unchecked")
    private void showSortedDEF() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();
        ArrayList<String> pokemonNames = new ArrayList<>();

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Pokemon p : allPokemon){
            map.put(p.getName(), p.getDefense());
        }
        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        for (Object e : a) {
            pokemonNames.add(((Map.Entry<String, Integer>) e).getKey());
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);

        listView.setItems(observableListPokemons);
    }

    //This is fine... no worries... it works :D
    @SuppressWarnings("unchecked")
    private void showSortedSPD() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();
        ArrayList<String> pokemonNames = new ArrayList<>();

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Pokemon p : allPokemon){
            map.put(p.getName(), p.getSpeed());
        }
        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        for (Object e : a) {
            pokemonNames.add(((Map.Entry<String, Integer>) e).getKey());
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);

        listView.setItems(observableListPokemons);
    }

    //This is fine... no worries... it works :D
    @SuppressWarnings("unchecked")
    private void showSortedPrice() {
        ArrayList<Pokemon> allPokemon = main.getAllPokemon();
        ArrayList<String> pokemonNames = new ArrayList<>();

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Pokemon p : allPokemon){
            map.put(p.getName(), p.getCost());
        }
        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        for (Object e : a) {
            pokemonNames.add(((Map.Entry<String, Integer>) e).getKey());
        }

        ObservableList<String> observableListPokemons = FXCollections.observableArrayList(pokemonNames);

        listView.setItems(observableListPokemons);
    }
}

