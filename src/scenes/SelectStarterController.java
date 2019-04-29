package scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.Main;
import pokemon.Pokemon;

public class SelectStarterController implements Controller {

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void reset(){
        //reset the selected starter and nickname
    }

    private Main main;
    private int starterID, starterSelected; // Starter Selected should only be values 1 -3
    private String nickname;

    // The following are used in the selectStarter Method
    @FXML
    TextField fieldNickname;

    @FXML
    Button btnNickname, btnNoNickname;

    @FXML
    Text txtStarterMain;

    @FXML
    ImageView leftStarter, middleStarter, rightStarter;

    // This just rewrites the scene it doesn't actually change it to a new scene disregard the name!
    private void sceneChange(){
        fieldNickname.setVisible(true);
        btnNickname.setVisible(true);
        btnNoNickname.setVisible(true);

        txtStarterMain.setText("\t\t\t\tGive Your New Friend a Name!");
    }

    public void setNickname(){
        nickname = fieldNickname.getText();
    }

    public void leftStarterPressed(){
        sceneChange();
        middleStarter.setVisible(false);
        rightStarter.setVisible(false);

        leftStarter.setLayoutX(225);

        starterSelected = 1;
    }

    public void middleStarterPressed(){
        sceneChange();
        rightStarter.setVisible(false);
        leftStarter.setVisible(false);

        starterSelected = 2;
    }

    public void rightStarterPressed(){
        sceneChange();
        leftStarter.setVisible(false);
        middleStarter.setVisible(false);

        rightStarter.setLayoutX(225);

        starterSelected = 3;
    }

    public void finishedSelecting(){
        main.selectedStarter(starterID);

        if (nickname != null){
            if (starterSelected == 1){
                // ************* THIS WILL BE REPLACED BY THE DATABASE ***************************************************
                Pokemon starter = new Pokemon(1,10,10,10,10, nickname,"Left");
            }
            if (starterSelected == 2){
                // ************* THIS WILL BE REPLACED BY THE DATABASE ***************************************************
                Pokemon starter = new Pokemon(2, 10, 10, 10, 10, nickname, "Middle");
                main.addPokemon(starter);
            }
            else if (starterSelected == 3) {
                // ************* THIS WILL BE REPLACED BY THE DATABASE ***************************************************
                Pokemon starter = new Pokemon(3, 10, 10, 10, 10, nickname, "Middle");
                main.addPokemon(starter);
            }
        }
        else {
            if (starterSelected == 1){
                // ************* THIS WILL BE REPLACED BY THE DATABASE ***************************************************
                Pokemon starter = new Pokemon(1, 10, 10, 10, 10, "Fitty", "Middle");
                main.addPokemon(starter);
            }
            if (starterSelected == 2){
                // ************* THIS WILL BE REPLACED BY THE DATABASE ***************************************************
                Pokemon starter = new Pokemon(2, 10, 10, 10, 10, "Owly", "Middle");
                main.addPokemon(starter);
            }
            else if (starterSelected == 3) {
                // ************* THIS WILL BE REPLACED BY THE DATABASE ***************************************************
                Pokemon starter = new Pokemon(3, 10, 10, 10, 10, "Wety", "Middle");
                main.addPokemon(starter);
            }
        }
    }
}
