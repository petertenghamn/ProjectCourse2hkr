package scenes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import main.pokemon.PokemonMapper;
import main.scenemanager.SceneManager;
import main.users.Trainer;
import main.users.User;

import java.util.ArrayList;

public class ViewTrainerController implements Controller {

    @Override
    public void setMain(Main m) {
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void setUp() {
        updateTrainers();

        listCollection.setVisible(false);
        listTeam.setVisible(false);

        chkBxAllowRemoval.setSelected(false);

        if (listView.getItems().size() > 0){
            listView.getSelectionModel().selectFirst();
            showImage();
            updateStats();
            showCollection();
            showTeam();
        }
    }

    @Override
    public void reset() {
        Image image = new Image("scenes/view/images/pokeLogo.png");
        imgView.setImage(image);

        chkBxAllowRemoval.setSelected(false);

        listCollection.setVisible(false);
        listTeam.setVisible(false);
    }

    private Main main;

    public void backToProfessorMenu() {
        main.requestSceneChange(SceneManager.sceneName.PROFESSORMENU);
    }

    @FXML
    TextField txtLosses, txtWins, txtName, txtCurrency;

    @FXML
    ImageView imgView;

    @FXML
    ListView listView, listTeam, listCollection;
    private String email;
    @FXML
    CheckBox chkBxAllowRemoval;

    public void selectTrainer() {
        chkBxAllowRemoval.setSelected(false);
        showImage();
        updateStats();
        showCollection();
        showTeam();
    }

    private void updateTrainers() {
        chkBxAllowRemoval.setSelected(false);

        ArrayList<User> trainers = main.getTrainers();
        ArrayList<String> names = new ArrayList<>();

        for (User trainer : trainers) {
            if (trainer instanceof Trainer) {
                names.add(((Trainer) trainer).getUsername());
            }
        }

        ObservableList<String> observableNames = FXCollections.observableArrayList(names);

        listView.setItems(observableNames);
    }

    private void showImage() {
        Image trainer;
        String name = listView.getSelectionModel().getSelectedItem().toString();
        int random = (int) ((Math.random() * ((3 - 1) + 1) + 0));

        if (name.equalsIgnoreCase("Ash")) {
            trainer = new Image("scenes/view/images/ash.png");
        }else if(name.equalsIgnoreCase("James")){trainer = new Image("scenes/view/images/james.png");
        } else if (random == 1) {
            trainer = new Image("scenes/view/images/boy.png");
        } else if (random == 2) {
            trainer = new Image("scenes/view/images/girl.png");
        } else if (random == 3) {
            trainer = new Image("scenes/view/images/RedheadGirlTrainer.png");
        } else {
            trainer = new Image("scenes/view/images/pokeLogo.png");
        }

        imgView.setImage(trainer);
    }

    private void updateStats() {
        String name = listView.getSelectionModel().getSelectedItem().toString();
        ArrayList<User> trainers = main.getTrainers();

        email = "";
        int currency = 0;
        int wins = 0;
        int losses = 0;

        for (User trainer : trainers){
            if (name.equalsIgnoreCase(((Trainer) trainer).getUsername())){
                email = ((Trainer) trainer).getEmail();
                currency = ((Trainer) trainer).getCurrency();
                wins = ((Trainer) trainer).getWinCount();
                losses = ((Trainer) trainer).getLossCount();
            }
        }

        txtName.setText(listView.getSelectionModel().getSelectedItem().toString());
        txtCurrency.setText(Integer.toString(currency));
        txtWins.setText(Integer.toString(wins));
        txtLosses.setText(Integer.toString(losses));
    }

    private void showCollection() {
        listCollection.setVisible(true);

        String name = listView.getSelectionModel().getSelectedItem().toString();

        ArrayList<User> trainers = main.getTrainers();
        ArrayList<PokemonMapper> collection = new ArrayList<>();

        for (User trainer : trainers) {
            if (name.equalsIgnoreCase(((Trainer) trainer).getUsername())) {
                collection = ((Trainer) trainer).getCollection();
            }
        }

        ArrayList<String> pokeNicknames = new ArrayList<>();

        for (PokemonMapper pokemon : collection) {
            pokeNicknames.add(pokemon.getNickname());
        }

        // Needed for a ListView in JavaFX for some reason
        ObservableList<String> collectionPokemon = FXCollections.observableArrayList(pokeNicknames);

        listCollection.setItems(collectionPokemon);
    }

    private void showTeam() {
        listTeam.setVisible(true);

        String name = listView.getSelectionModel().getSelectedItem().toString();

        ArrayList<User> trainers = main.getTrainers();
        ArrayList<PokemonMapper> team = new ArrayList<>();

        for (User trainer : trainers) {
            if (name.equalsIgnoreCase(((Trainer) trainer).getUsername())) {
                team = ((Trainer) trainer).getTeam();

            }
        }

        ArrayList<String> pokeNicknames = new ArrayList<>();

        for (PokemonMapper pokemon : team) {
            pokeNicknames.add(pokemon.getNickname());
        }

        // Needed for a ListView in JavaFX for some reason
        ObservableList<String> collectionPokemon = FXCollections.observableArrayList(pokeNicknames);

        listTeam.setItems(collectionPokemon);
    }

    public void updateTrainer(){
        if (!txtName.getText().isEmpty() && Integer.parseInt(txtWins.getText()) >= 0 && Integer.parseInt(txtLosses.getText()) >= 0 && Integer.parseInt(txtCurrency.getText()) >= 0) {
            ArrayList<User> trainers = main.getTrainers();

            for (User trainer : trainers) {
                if (trainer instanceof Trainer) {
                    if (((Trainer) trainer).getEmail().equals(email)) {
                        ((Trainer) trainer).setUsername(txtName.getText());
                        ((Trainer) trainer).setWinCount(Integer.parseInt(txtWins.getText()));
                        ((Trainer) trainer).setLossCount(Integer.parseInt(txtLosses.getText()));
                        ((Trainer) trainer).setCurrency(Integer.parseInt(txtCurrency.getText()));

                        main.editTrainerStats(trainer);
                        updateTrainers();
                    }
                }
            }
        }
        else {
            if (txtName.getText().isEmpty()) {
                System.out.println("Name cannot be empty!");
            }
            else if (Integer.parseInt(txtWins.getText()) < 0){
                System.out.println("Wins cannot be negative!");
            }
            else if (Integer.parseInt(txtLosses.getText()) < 0){
                System.out.println("Losses cannot be negative!");
            }
            else if (Integer.parseInt(txtCurrency.getText()) < 0){
                System.out.println("Currency cannot be negative!");
            }
        }
    }

    public void removeTrainer(){
        if (chkBxAllowRemoval.isSelected()){
            ArrayList<User> trainers = main.getTrainers();

            for (User trainer : trainers) {
                if (trainer instanceof Trainer) {
                    if (((Trainer) trainer).getEmail().equals(email)) {
                        main.removeTrainer(trainer);

                        //update the list shown
                        reset();
                        setUp();
                    }
                }
            }
        }
        else {
            System.out.println("Removal denied due to box not being checked!");
        }
    }
}
