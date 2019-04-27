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
    private int starterID;

    // The following are used in the selectStarter Method
    @FXML
    TextField fieldNickname;

    @FXML
    Button btnNickname;

    @FXML
    Text txtStarterMain;

    @FXML
    ImageView FireCat, GrassOwl, WaterSeal;

    public void selectStarter(){
        txtStarterMain.setVisible(false);
        fieldNickname.setVisible(true);
        btnNickname.setVisible(true);

        String nickname = "NoName";
        boolean wantNickname = false;

        if (btnNickname.isPressed()) {
            wantNickname = true;

            nickname = fieldNickname.getText();
        }

        if (wantNickname){
            if (FireCat.isPressed()) {
                GrassOwl.setVisible(false);
                WaterSeal.setVisible(false);

                starterID = 1;
            }
            else if (GrassOwl.isPressed()){
                WaterSeal.setVisible(false);
                FireCat.setVisible(false);

                starterID = 2;

            }
            else if (WaterSeal.isPressed()){
                GrassOwl.setVisible(false);
                FireCat.setVisible(false);

                starterID = 3;

            }
        }
        else{
            if (FireCat.isPressed()) {
                GrassOwl.setVisible(false);
                WaterSeal.setVisible(false);

                starterID = 1;
            }
            else if (GrassOwl.isPressed()){
                WaterSeal.setVisible(false);
                FireCat.setVisible(false);

                starterID = 2;

            }
            else if (WaterSeal.isPressed()){
                GrassOwl.setVisible(false);
                FireCat.setVisible(false);

                starterID = 3;

            }
        }
    }

    public void finishedSelecting(){
        main.selectedStarter(starterID);
    }
}
