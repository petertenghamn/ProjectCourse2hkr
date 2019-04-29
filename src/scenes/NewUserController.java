package scenes;

import javafx.fxml.FXML;
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
    }

    @FXML
    TextField email;
    @FXML
    PasswordField password, passwordConfirm;

    public void goButton(){
        //verify that fields are not empty then send to main
        if (!email.getText().isEmpty() && !password.getText().isEmpty() && !passwordConfirm.getText().isEmpty()){
            if (password.getText().equals(passwordConfirm.getText())) {
                main.createNewUser(email.getText(), password.getText());
            }
            else {
                System.out.println("Passwords DO NOT match!");
            }
        }
        else {
            System.out.println("Either email or password is empty");
        }
    }

    public void backButton(){
        main.requestSceneChange(SceneManager.sceneName.LOGIN);
    }
}
