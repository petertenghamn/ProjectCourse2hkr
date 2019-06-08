package scenes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.DebugDatabase;
import main.Main;
import main.scenemanager.SceneManager;

public class LoginController implements Controller {

    @FXML
    Button btnLogin, btnCreateNA;
    @FXML
    TextField txtFieldUsername;
    @FXML
    PasswordField txtFieldPassword;
    @FXML
    Label lblErrorLogin, lblDebug;
    @FXML
    AnchorPane anc;
    @FXML
    ImageView imgView;

    private Main main;

    @Override
    public void setMain(Main m) {
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void reset() {
        txtFieldUsername.setText("");
        txtFieldPassword.setText("");
        lblErrorLogin.setText("");
    }

    public void loginRequest() {
        String email = txtFieldUsername.getText();
        String password = txtFieldPassword.getText();
        if (email.isEmpty() && password.isEmpty()) {
            lblErrorLogin.setText("Email and password are empty.");
        } else if (email.isEmpty()) {
            lblErrorLogin.setText("Email is empty.");
        } else if (!txtFieldUsername.getText().contains("@")) {
            lblErrorLogin.setText("The email must contain @.");
        } else if (password.isEmpty()) {
            lblErrorLogin.setText("Password is empty.");
        } else if (!main.authenticateLogin(email, password)) {
            lblErrorLogin.setText("The information you entered is incorrect.");
        }
    }

    public void newUser() {
        main.requestSceneChange(SceneManager.sceneName.NEWUSER);
    }

    public void debugDB(){
        //not implemented fully
        /*
        lblDebug.setText("Debug controller activated.");
        main.setDeBugLoader(true);
        main.setAllPokemon();
        */
    }
}
