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

    public DebugDatabase(){
        pokemons = new ArrayList<>();
        pokemons.add(new Pokemon(1,"Seaturtle",20,10,40,5, 50, "Earth"));
        pokemons.add(new Pokemon(2,"Flappybird", 10,30,10,25, 25,"Wind"));
        pokemons.add(new Pokemon(3, "Bulbasaur", 100, 10, 10, 10, 50, "Grass, Poison"));
        pokemons.add(new Pokemon(4, "Ivysaur", 100, 10, 10, 10, 75, "Grass, Poison"));
        pokemons.add(new Pokemon(5, "Venusaur", 100, 10, 10, 10, 100, "Grass, Poison"));
        pokemons.add(new Pokemon(6, "Charizard", 100, 10, 10, 10, 100, "Fire, Flying"));
        pokemons.add(new Pokemon(7, "Charmander", 100, 10, 10, 10, 50, "Fire"));
        pokemons.add(new Pokemon(8, "Charmeleon", 100, 10, 10, 10, 75, "Fire"));
        pokemons.add(new Pokemon(9, "Squirtle", 100, 10, 10, 10, 50, "Water"));
        pokemons.add(new Pokemon(10, "Wartortle", 100, 10, 10, 10, 75, "Water"));
        pokemons.add(new Pokemon(11, "Blastoise", 100, 10, 10, 10, 100, "Water"));
        pokemons.add(new Pokemon(12, "Pikachu", 100, 10, 10, 10, 100, "Electric"));
        pokemons.add(new Pokemon(13, "Raichu", 100, 10, 10, 10, 100, "Electric"));

        users = new ArrayList<>();
        users.add(new Professor("oak@prof"));
        ArrayList<PokemonMapper> mapper = new ArrayList<>();
        mapper.add(new PokemonMapper(3, "Bulby"));
        users.add(new Trainer("ash@train", "Ash", 100, 0, 0, mapper, new ArrayList<>()));
        mapper.clear();
        mapper.add(new PokemonMapper(12, "Pika"));
        users.add(new Trainer("mario@train", "Mario", 100, 0, 0, mapper, new ArrayList<>()));

        userPasswords = new HashMap<>();
        for (User u : users){
            if (u instanceof Trainer){
                userPasswords.put(((Trainer) u).getEmail(), "root");
            } else if (u instanceof Professor){
                userPasswords.put(((Professor) u).getEmail(), "root");
            }
        }
    }

    public ArrayList<User> getTrainers(){
        ArrayList<User> trainers = new ArrayList<>();
        for (User u : users){
            if (u instanceof Trainer){
                trainers.add(u);
            }
        }
        return trainers;
    }

    public ArrayList<Pokemon> getPokemons(){
        return pokemons;
    }

    public void addPokemon(Pokemon pokemon){
        pokemons.add(pokemon);
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

