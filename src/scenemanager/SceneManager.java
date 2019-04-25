package scenemanager;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Main;
import scenemanager.loader.FileLoader;
import scenes.Controller;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private FileLoader fileLoader;
    private Stage primaryStage;

    //enum to store all the names of possible scenes to change to
    public enum sceneName { MISSING, LOGIN, NEWUSER, SELECTSTARTER, TRAINERMENU, PROFESSORMENU }
    private sceneName getEnumFromID(String id){
        if (id.equalsIgnoreCase("poke")){
            return sceneName.SELECTSTARTER;
        }
        else if (id.equalsIgnoreCase("login")){
            return sceneName.LOGIN;
        }
        else {
            System.out.println("Missing enum assignment in SceneManager: " + id);
            return sceneName.MISSING;
        }
    }
    private Map<sceneName, SceneMapper> sceneMap;

    private SceneMapper currentScene;

    public SceneManager(Stage ps){
        primaryStage = ps;
        sceneMap = new HashMap<>();
    }

    public <T extends Controller,K extends Parent> void addScene(String id, K scene, T controller){
        sceneName enumID = getEnumFromID(id);

        sceneMap.put(
                enumID,
                new SceneMapper<T,K>(
                        controller,
                        scene
                )
        );

    }

    //loads all scenes in the folder labeled scenes
    public void loadScenes(){
        fileLoader = new FileLoader("fxml", this);
        fileLoader.collect();
    }

    public void changeScene(sceneName request){
        //change the scene view and controller to requested scene
            primaryStage.setTitle(request.toString());
            currentScene = sceneMap.get(request);
            primaryStage.setScene(new Scene(currentScene.getParent()));
            primaryStage.show();
    }

    public void setMainToController(Main m){
        currentScene.getController().setMain(m);
    }
}
