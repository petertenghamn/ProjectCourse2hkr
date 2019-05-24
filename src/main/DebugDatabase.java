package main;

import main.pokemon.Pokemon;
import main.pokemon.PokemonMapper;
import main.users.Professor;
import main.users.Trainer;
import main.users.User;

import java.util.ArrayList;
import java.util.HashMap;

public class DebugDatabase {

    private ArrayList<Pokemon> pokemons;
    private ArrayList<User> users;
    private HashMap<String, String> userPasswords;
    private PokemonMapper pokemap;
    private ArrayList<String> ashCollection = new ArrayList<>();
    private ArrayList<Pokemon> marioCollection = new ArrayList<>();

    public ArrayList<String> getAshCollection() {
        return ashCollection;
    }

    public DebugDatabase(){
        pokemons = new ArrayList<>();
        pokemons.add(new Pokemon(1,"Seaturtle",20,10,40,5,"Earth"));
        pokemons.add(new Pokemon(2,"Flappybird", 10,30,10,25,"Wind"));
        pokemons.add(new Pokemon(3, "Bulbasaur", 100, 10, 10, 10, "Grass, Poison"));
        pokemons.add(new Pokemon(4, "Ivysaur", 100, 10, 10, 10, "Grass, Poison"));
        pokemons.add(new Pokemon(5, "Venusaur", 100, 10, 10, 10, "Grass, Poison"));
        pokemons.add(new Pokemon(6, "Charizard", 100, 10, 10, 10, "Fire, Flying"));
        pokemons.add(new Pokemon(7, "Charmander", 100, 10, 10, 10, "Fire"));
        pokemons.add(new Pokemon(8, "Charmeleon", 100, 10, 10, 10, "Fire"));
        pokemons.add(new Pokemon(9, "Squirtle", 100, 10, 10, 10, "Water"));
        pokemons.add(new Pokemon(10, "Wartortle", 100, 10, 10, 10, "Water"));
        pokemons.add(new Pokemon(11, "Blastoise", 100, 10, 10, 10, "Water"));
        pokemons.add(new Pokemon(12, "Pikachu", 100, 10, 10, 10, "Electric"));
        pokemons.add(new Pokemon(13, "Raichu", 100, 10, 10, 10, "Electric"));

        users = new ArrayList<>();
        users.add((new Professor("oak@prof") {
        }));
        users.add((new Trainer("ash@train","ash",100,0,0, new ArrayList<>(), new ArrayList<>())));
        users.add((new Trainer("mario@train","mario",100,0,0, new ArrayList<>(), new ArrayList<>())));
        ashCollection.add(pokemons.get(1).getName());
        ashCollection.add(pokemons.get(4).getName());
        ashCollection.add(pokemons.get(6).getName());
        ashCollection.add(pokemons.get(8).getName());
        marioCollection.add(pokemons.get(2));
        marioCollection.add(pokemons.get(7));
        marioCollection.add(pokemons.get(8));
        marioCollection.add(pokemons.get(11));
        marioCollection.add(pokemons.get(12));

        userPasswords = new HashMap<>();
        for (User u : users){
            if (u instanceof Trainer){
                userPasswords.put(((Trainer) u).getEmail(), "root");
            } else if (u instanceof Professor){
                userPasswords.put(((Professor) u).getEmail(), "root");
            }
        }
    }
    public ArrayList<Pokemon> getMarioCollection(){
        ArrayList<Pokemon> arrayList = new ArrayList<>();
        arrayList.add(pokemons.get(2));
        arrayList.add(pokemons.get(4));
        arrayList.add(pokemons.get(7));
        return arrayList;
    }

    public ArrayList<Pokemon> getPokemons(){
        return pokemons;
    }

    public User authenticateUser(String username, String password) {
        System.out.println(username);
        for (User u : users){
            if (u instanceof Trainer) {
                if (((Trainer) u).getEmail().equals(username)) {
                    if (password.equals(userPasswords.get(((Trainer) u).getEmail()))) {
                        return u;
                    }
                }
            }
            else if (u instanceof Professor) {
                if (((Professor) u).getEmail().equals(username))
                {
                    if (password.equals(userPasswords.get(((Professor) u).getEmail()))) {
                        return u;
                    }
                }
            }
        }
        return null;
    }}

