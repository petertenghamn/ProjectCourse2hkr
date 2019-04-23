package scenemanager;

import javafx.scene.Scene;

import java.util.HashMap;

public class SceneManager {
    //enum to store all the names of possible scenes to change to
    private enum sceneName { LOGIN, NEWUSER, SELECTSTARTER, TRAINERMENU, PROFESSORMENU }
    private HashMap<sceneName, Scene> scene;

    private sceneName currentScene;

    public SceneManager(){
        //initialize scenes

        changeScene(sceneName.LOGIN);
    }

    public void changeScene(sceneName request){
        //change the scene view and controller to requested scene

        currentScene = request;
    }
}
