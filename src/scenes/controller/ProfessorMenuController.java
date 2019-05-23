package scenes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.Main;
import main.scenemanager.SceneManager;
import main.users.Professor;
import main.users.User;

public class ProfessorMenuController implements Controller {

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void setUp(){
        updateStats();
    }

    @Override
    public void reset(){

    }

    @FXML
    Label lblEmail, lblTrainers, lblPokemons;

    private Main main;

    private void updateStats(){
        User user = main.getCurrentUser();

        lblEmail.setText(((Professor) user).getEmail());

        Integer trainers = main.getTrainers().size();
        Integer pokemons = main.getAllPokemon().size();

        lblTrainers.setText(trainers.toString());
        lblPokemons.setText(pokemons.toString());
    }

    public void showTrainers(){
        main.requestSceneChange(SceneManager.sceneName.VIEWTRAINER);
    }

    public void viewAllPokemon(){
        main.requestSceneChange(SceneManager.sceneName.SHOWALLPOKEMON);
    }

    public void logoutButton(){
        main.logoutUser();
    }
}
