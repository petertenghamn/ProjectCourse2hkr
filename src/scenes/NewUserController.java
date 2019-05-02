package scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;
import scenemanager.SceneManager;

public class NewUserController implements Controller {

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    private Main main;

    @Override
    public void reset(){
        email.setText("");
        password.setText("");
        passwordConfirm.setText("");
        errorLabel.setText("");
    }
    @FXML
    Label errorLabel;
    @FXML
    TextField email;
    @FXML
    PasswordField password, passwordConfirm;

    public void goButton(){
        //verify that fields are not empty then send to main
        if (!email.getText().isEmpty() && !password.getText().isEmpty() && !passwordConfirm.getText().isEmpty() && email.getText().contains("@")){
            if (password.getText().equals(passwordConfirm.getText())) {
                main.createNewUser(email.getText(), password.getText());
            }
            else {
                errorLabel.setText("Passwords DO NOT match!");
            }
        }
        else if(!email.getText().contains("@")){
            errorLabel.setText("The email needs to contain a @");
        }
        else {
            errorLabel.setText("Either email or password is empty");
        }
    }

    public void backButton(){
        main.requestSceneChange(SceneManager.sceneName.LOGIN);
    }
}
