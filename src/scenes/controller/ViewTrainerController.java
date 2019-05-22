package scenes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import main.scenemanager.SceneManager;
import java.util.ArrayList;

public class ViewTrainerController implements Controller{

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void setUp(){
        updateTrainers();

        listCollection.setVisible(false);
        listTeam.setVisible(false);
    }

    @Override
    public void reset(){
        Image image = new Image("scenes/view/images/pokeLogo.png");
        imgView.setImage(image);

        listTeam.setVisible(false);
        listTeam.setVisible(false);
    }

    private Main main;

    public void backToProfessorMenu() {
        main.requestSceneChange(SceneManager.sceneName.PROFESSORMENU);
    }

    @FXML
    Label lblLosses, lblWins, lblName, lblCurrency;

    @FXML
    ImageView imgView;

    @FXML
    ListView listView, listTeam, listCollection;

    public void selectTrainer(){
        showImage();
        updateStats();
        showCollection();
        showTeam();
    }

    private void updateTrainers(){
        /* DON'T UNCOMMENT UNTIL THE Main.getTrainers() WORKS!
        User[] trainers = main.getTrainers();
        ArrayList<String> names = new ArrayList<>();

        for (User trainer : trainers){
            names.add(((Trainer) trainer).getUsername());
        }
         DELETE THE ARRAY LIST CREATED BELOW!!!!!!!!!!!!!!!!!!!! */
        ArrayList<String> names = new ArrayList<>();
        names.add("Ash");
        names.add("Peter");
        names.add("Viktor");
        names.add("Gary");
        names.add("Brock");

        ObservableList<String> observableNames = FXCollections.observableArrayList(names);

        listView.setItems(observableNames);

    }

    private void showImage(){
        Image trainer;

        // The Numbers represent the max - min + 1 + min
        // This should be based on the amount of Trainer Pictures
        int random = (int)((Math.random()*((4-1) +1)+ 1));

        switch (random){
            case 1:{
                trainer = new Image("scenes/view/images/ash.png");
                break;
            }
            case 2:{
                trainer = new Image("scenes/view/images/boy.png");
                break;
            }
            case 3:{
                trainer = new Image("scenes/view/images/girl.png");
                break;
            }
            case 4:{
                trainer = new Image("scenes/view/images/RedheadGirlTrainer.png");
                break;
            }
            default:{
                trainer = new Image("scenes/view/images/pokeLogo.png");
            }
        }

        imgView.setImage(trainer);
    }

    // THIS NEEDS TO BE DONE AS IT'S A WORK AROUND!!
    private void updateStats(){
        int randomCurrency = (int)((Math.random()*((2500) +1)+ 0));
        int randomWin = (int)((Math.random()*((4) +1)+ 0));
        int randomLoss = (int)((Math.random()*((4-1) +1)+ 0));

        lblName.setText(listView.getSelectionModel().getSelectedItem().toString());
        lblCurrency.setText(Integer.toString(randomCurrency));
        lblWins.setText(Integer.toString(randomWin));
        lblLosses.setText(Integer.toString(randomLoss));
    }

    private void showCollection(){
        listCollection.setVisible(true);
    }

    private void showTeam(){
        listTeam.setVisible(true);
    }
}
