package scenes;

import main.Main;
import pokemon.PokemonMapper;
import users.Trainer;
import users.User;

public class AcquirePokemonController implements Controller {

    private Main main;

    @Override
    public void setMain(Main m){
        //set the main so that you can call upon it to change scenes
        main = m;
    }

    public void reset(){

    }

    private int pokemonSelected = main.getPokemonIDGlobal(); // Pokemon Selected should be max value the number of Pokemon in our database!!
    private String nickname = main.getPokemonNicknameGlobal();

    public void acquirePokemon(){
        User user = main.getCurrentUser();
        if (user instanceof Trainer){
            // Added for extra security change the values depending on highest ID number of pokemon the database has!  CURRENT HIGHEST = RAICHU 26
            if (pokemonSelected > 0 && pokemonSelected <= 26) {
                // Taken from the main ********************************************* MIGHT NOT BE THE CORRECT WAY TO ADD
                PokemonMapper pokemon = new PokemonMapper(pokemonSelected, nickname);
                ((Trainer) user).addToCollection(pokemon);
            }
        }
    }
}
