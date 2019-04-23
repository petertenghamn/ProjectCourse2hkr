package Pokemon;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PokeController {

    // This is used to change the scene ***** NOT YET IMPLEMENTED
    private Parent root;

    public void activeStage(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Pokemon");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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
