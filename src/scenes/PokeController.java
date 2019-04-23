package scenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pokemon.Pokemon;

import java.net.URL;
import java.util.ResourceBundle;

public class PokeController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //for now empty (needed for "implements Initializable")
        //can assign variables here that need to be assigned after variables are declared
    }

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

                Pokemon starter = new Pokemon(10,10,10,10, nickname,"Fire");
            }
            else if (GrassOwl.isPressed()){
                WaterSeal.setVisible(false);
                FireCat.setVisible(false);

                Pokemon starter = new Pokemon(10,10,10,10, nickname, "Grass");

            }
            else if (WaterSeal.isPressed()){
                GrassOwl.setVisible(false);
                FireCat.setVisible(false);

                Pokemon starter = new Pokemon(10,10,10,10, nickname, "Water");

            }
        }
        else{
            if (FireCat.isPressed()) {
                GrassOwl.setVisible(false);
                WaterSeal.setVisible(false);

                Pokemon starter = new Pokemon(10,10,10,10, "FireCat","Fire");
            }
            else if (GrassOwl.isPressed()){
                WaterSeal.setVisible(false);
                FireCat.setVisible(false);

                Pokemon starter = new Pokemon(10,10,10,10, "GrassOwl", "Grass");

            }
            else if (WaterSeal.isPressed()){
                GrassOwl.setVisible(false);
                FireCat.setVisible(false);

                Pokemon starter = new Pokemon(10,10,10,10, "WaterSeal", "Water");

            }
        }
    }

}
