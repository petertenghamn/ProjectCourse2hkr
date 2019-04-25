package scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.Main;

public class LoginController implements Controller {

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    private Main main;

    @FXML
    Button btnLogin;
    @FXML
    Button btnCreateNA;
    @FXML
    TextField txtFieldUsername;
    @FXML
    TextField txtFieldPassword;
    @FXML
    AnchorPane anc;
    @FXML
    ImageView imgView;

    public void loginRequest(){
        main.loginRequested();
    }
}
