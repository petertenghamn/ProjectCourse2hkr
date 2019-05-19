package scenes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;
import main.scenemanager.SceneManager;

public class NewUserController implements Controller {

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    private Main main;

    @Override
    public void setUp(){

    }

    @Override
    public void reset(){
        username.setText("");
        email.setText("");
        password.setText("");
        passwordConfirm.setText("");
        errorLabelEmail.setText("");
        errorLabelPassword.setText("");
        errorLabelUsername.setText("");
    }
    @FXML
    Label errorLabelUsername, errorLabelEmail, errorLabelPassword;
    @FXML
    TextField username, email;
    @FXML
    PasswordField password, passwordConfirm;

    public void goButton(){
        //verify that fields are not empty then send to main
        if (!username.getText().isEmpty() && !email.getText().isEmpty() && !password.getText().isEmpty() && !passwordConfirm.getText().isEmpty() && email.getText().contains("@")){
            if (password.getText().equals(passwordConfirm.getText())) {
                main.createNewUser(email.getText(), username.getText(), password.getText());
            }
            else {
                errorLabelPassword.setText("Passwords DO NOT match!");
            }
        }
        if (!email.getText().contains("@")){
            errorLabelEmail.setText("The email needs to contain an @");
        }
        if (username.getText().isEmpty()){
            errorLabelUsername.setText("Username is empty!");
        }
        if (email.getText().isEmpty()){
            errorLabelEmail.setText("Email is empty!");
        }
        if (password.getText().isEmpty() && !passwordConfirm.getText().isEmpty()){
            errorLabelPassword.setText("Password is empty!");
        }
        if (passwordConfirm.getText().isEmpty() && !password.getText().isEmpty()){
            errorLabelPassword.setText("Password Confirm is empty!");
        }
    }

    public void backButton(){
        main.requestSceneChange(SceneManager.sceneName.LOGIN);
    }
}
