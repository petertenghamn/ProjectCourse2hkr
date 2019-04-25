package scenes;

import main.Main;
import scenemanager.SceneManager;

public class TrainerMenuController implements Controller {

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    private Main main;

    public void logoutButton(){
        main.requestSceneChange(SceneManager.sceneName.LOGIN);
    }
}