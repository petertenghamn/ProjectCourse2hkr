package scenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //for now empty (needed for "implements Initializable")
        //can assign variables here that need to be assigned after variables are declared
    }

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
}
