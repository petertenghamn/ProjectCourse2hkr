package scenemanager;

import javafx.fxml.Initializable;
import org.w3c.dom.Node;

public class SceneMapper<T extends Initializable, K extends Node> {
    private T controller;
    private K scene;

    SceneMapper(T controller, K scene){
        this.controller = controller;
        this.scene = scene;
    }

    public T getController(){
        return controller;
    }

    public K getScene(){
        return scene;
    }
}
