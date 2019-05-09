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

    private Main main;

    private FileLoader fileLoader;
    private Stage root;
    private Scene primaryScene;

    //enum to store all the names of possible scenes to change to
    public enum sceneName { MISSING, LOGIN, NEWUSER, SELECTSTARTER, TRAINERMENU, PROFESSORMENU, SHOWALLPOKEMON }
    private sceneName getEnumFromID(String id){
        //setup each menu to an enum so its easier to call on them later
        if (id.equalsIgnoreCase("login")){
            return sceneName.LOGIN;
        }
        else if (id.equalsIgnoreCase("newuser")){
            return sceneName.NEWUSER;
        }

        // Start of the Trainer Scenes
        else if (id.equalsIgnoreCase("trainermenu")){
            return sceneName.TRAINERMENU;
        }
        else if (id.equalsIgnoreCase("selectstarter")){
            return sceneName.SELECTSTARTER;
        }

        // This one is shared by both trainer and professor
        /*
        else if (id.equalsIgnoreCase("showallpokemon")){
            return sceneName.SHOWALLPOKEMON;
        }
        */

        else if (id.equalsIgnoreCase("professormenu")){
            return sceneName.PROFESSORMENU;
        }
        else {
            System.out.println("Missing enum assignment in SceneManager: " + id);
            return sceneName.MISSING;
        }
    }
    private Map<sceneName, SceneMapper> sceneMap;
    private SceneMapper currentScene;

    public SceneManager(Main m, Stage ps){
        main = m;

        root = ps;
        sceneMap = new HashMap<>();
        loadScenes();

        root.setTitle("PokeDB");
        currentScene = sceneMap.get(sceneName.LOGIN);
        currentScene.getController().setMain(main);
        primaryScene = new Scene(currentScene.getParent());
        root.setScene(primaryScene);
        root.show();
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
    private void loadScenes(){
        fileLoader = new FileLoader("fxml", this);
        fileLoader.collect();
    }

    public void changeScene(sceneName request) {
        //reset previous scene
        currentScene.getController().reset();

        //change the scene view and controller to requested scene
        currentScene = sceneMap.get(request);
        currentScene.getController().setMain(main);
        primaryScene.setRoot(currentScene.getParent());
    }
}
