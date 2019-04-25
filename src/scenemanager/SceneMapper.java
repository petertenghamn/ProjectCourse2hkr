package scenemanager;

import javafx.scene.Parent;
import scenes.Controller;

public class SceneMapper<T extends Controller, K extends Parent> {
    private T controller;
    private K root;

    SceneMapper(T controller, K root){
        this.controller = controller;
        this.root = root;
    }

    public T getController(){
        return controller;
    }

    public K getParent(){
        return root;
    }
}
