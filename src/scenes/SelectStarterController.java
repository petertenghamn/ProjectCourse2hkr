package scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.Main;

public class SelectStarterController implements Controller {

    // The following are used in the selectStarter Method
    @FXML
    TextField fieldNickname;
    @FXML
    Button btnNickname, btnNoNickname, btnBack;
    @FXML
    Text txtStarterMain;
    @FXML
    ImageView leftStarter, middleStarter, rightStarter;
    @FXML
    Label labelCharmander, labelBulbasaur,labelSquirtle;

    private Main main;
    private int starterSelected; // Starter Selected should only be values 1 -3
    private String nickname;

    @Override
    public void setMain(Main m) {
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    @Override
    public void reset() {
        //reset the selected starter and nickname
        fieldNickname.setVisible(false);
        btnNickname.setVisible(false);
        btnNoNickname.setVisible(false);

        leftStarter.setVisible(true);
        leftStarter.setLayoutX(61);
        leftStarter.setLayoutY(152);
        labelCharmander.setVisible(true);
        labelCharmander.setLayoutX(115);
        labelCharmander.setLayoutY(444);

        middleStarter.setVisible(true);
        middleStarter.setLayoutX(339);
        middleStarter.setLayoutY(444);
        labelBulbasaur.setVisible(true);
        labelBulbasaur.setLayoutX(434);
        labelBulbasaur.setLayoutY(726);

        rightStarter.setVisible(true);
        rightStarter.setLayoutX(623);
        rightStarter.setLayoutY(152);
        labelSquirtle.setVisible(true);
        labelSquirtle.setLayoutX(718);
        labelSquirtle.setLayoutY(444);
        btnBack.setVisible(false);
        fieldNickname.clear();
    }

    // Changes internal scene within this controller, does not full switch scene
    private void sceneChange() {
        fieldNickname.setVisible(true);
        btnNickname.setVisible(true);
        btnNoNickname.setVisible(true);
        btnBack.setVisible(true);

        txtStarterMain.setText("Give your new friend a name!");
    }

    public void setNickname() {
        nickname = fieldNickname.getText();
        finishedSelecting();
    }

    public void leftStarterPressed() {
        sceneChange();
        middleStarter.setVisible(false);
        rightStarter.setVisible(false);
        labelBulbasaur.setVisible(false);
        labelSquirtle.setVisible(false);

        leftStarter.setLayoutX(352);
        leftStarter.setLayoutY(438);
        labelCharmander.setLayoutX(409);
        labelCharmander.setLayoutY(726);

        starterSelected = 1;
    }

    public void middleStarterPressed() {
        sceneChange();
        rightStarter.setVisible(false);
        leftStarter.setVisible(false);
        labelCharmander.setVisible(false);
        labelSquirtle.setVisible(false);

        middleStarter.setLayoutX(352);
        middleStarter.setLayoutY(438);
        labelBulbasaur.setLayoutX(436);
        labelBulbasaur.setLayoutY(719);

        starterSelected = 2;
    }

    public void rightStarterPressed() {
        sceneChange();
        leftStarter.setVisible(false);
        middleStarter.setVisible(false);
        labelCharmander.setVisible(false);
        labelBulbasaur.setVisible(false);

        rightStarter.setLayoutX(352);
        rightStarter.setLayoutY(438);
        labelSquirtle.setLayoutX(444);
        labelSquirtle.setLayoutY(737);

        starterSelected = 3;
    }

    public void backToStarters(){
        reset();
    }

    public void finishedSelecting() {
        //sends the pokemonID that the player selected to the main to assign it to their collection
        int starterID = (starterSelected == 1)?4:(starterSelected == 2)?1:7;
        main.selectedStarter(starterID, nickname);
    }

}
