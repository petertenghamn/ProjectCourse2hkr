package scenes.controller;

import main.Main;
import main.scenemanager.SceneManager;

public class ViewTrainerController implements Controller{

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void setUp(){

    }

    @Override
    public void reset(){

    }

    private Main main;

    public void backToProfessorMenu() {
        main.requestSceneChange(SceneManager.sceneName.PROFESSORMENU);
    }


}
