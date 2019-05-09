package scenes;

import main.Main;
import scenemanager.SceneManager;

public class ProfessorMenuController implements Controller {

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    public void reset(){

    }

    private Main main;

    public void logoutButton(){
        main.logoutUser();
    }
}
