package scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.Main;

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
    private int starterSelected; // Starter Selected should only be values 1 -3
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
        //sends the pokemonID that the player selected to the main to assign it to their collection
        main.selectedStarter(starterSelected, nickname);
    }
}
