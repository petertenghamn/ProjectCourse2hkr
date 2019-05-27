package scenes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Main;

public class LoginBonusController implements Controller {

    @FXML
    Button btnConfirm;

    public void setMain(Main m){

    }

    public void setUp(){

    }

    public void reset(){

    }

    public void confirm(){
        // get a handle to the stage
        Stage stage = (Stage) btnConfirm.getScene().getWindow();
        // close itself
        stage.close();
    }
}
