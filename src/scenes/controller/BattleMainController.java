package scenes.controller;

import main.Main;
import main.scenemanager.SceneManager;

public class BattleMainController implements Controller {

    private Main main;

    @Override
    public void setMain(Main m) {
        main = m;
    }
    @Override
    public void setUp(){
    }

    @Override
    public void reset(){

    }

    public void backToTrainerMenu(){
        main.requestSceneChange(SceneManager.sceneName.TRAINERMENU);
    }
}
