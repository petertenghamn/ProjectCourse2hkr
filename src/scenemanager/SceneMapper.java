package scenemanager;

import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class SceneMapper<T extends Initializable, K extends Parent> {
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
