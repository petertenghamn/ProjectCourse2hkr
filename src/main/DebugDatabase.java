package main;

import main.pokemon.Pokemon;
import main.users.User;
import scenes.controller.LoginController;

import java.util.ArrayList;

public class DebugDatabase {
    LoginController login;
    User []users = new User[2];
    Pokemon[] pokemons = new Pokemon[13];

    public User[] createUsers() {
        users[0] = (new User("oak@prof") {
            @Override
            protected void setPassword(String password) {
                super.setPassword("root");
            }
        });
        users[1] = (new User("james@player") {
            @Override
            protected void setPassword(String password) {
                super.setPassword("root");
            }
        });
        return users;
    }
    public Pokemon[] getPokemons(){
        pokemons[0] = new Pokemon(1,"Seaturtle",20,10,40,5,"Earth");
        pokemons[1] = new Pokemon(2,"Flappybird", 10,30,10,25,"Wind");
        pokemons[2] = new Pokemon(3, "Bulbasaur", 100, 10, 10, 10, "Grass, Poison");
        pokemons[3] = new Pokemon(4, "Ivysaur", 100, 10, 10, 10, "Grass, Poison");
        pokemons[4] = new Pokemon(5, "Venusaur", 100, 10, 10, 10, "Grass, Poison");
        pokemons[5] = new Pokemon(6, "Charizard", 100, 10, 10, 10, "Fire, Flying");
        pokemons[6] = new Pokemon(7, "Charmander", 100, 10, 10, 10, "Fire");
        pokemons[7] = new Pokemon(8, "Charmeleon", 100, 10, 10, 10, "Fire");
        pokemons[8] = new Pokemon(9, "Squirtle", 100, 10, 10, 10, "Water");
        pokemons[9] = new Pokemon(10, "Wartortle", 100, 10, 10, 10, "Water");
        pokemons[10] = new Pokemon(11, "Blastoise", 100, 10, 10, 10, "Water");
        pokemons[11] = new Pokemon(12, "Pikachu", 100, 10, 10, 10, "Electric");
        pokemons[12] = new Pokemon(13, "Raichu", 100, 10, 10, 10, "Electric");
        return pokemons;
    }
    public boolean authenticateUser() {
        User[] users = createUsers();
        String username;
        username = login.getTxtFieldUsername();
        System.out.println(username);
        for (User u : users){
            if (u.toString().contains(username)) {
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }}

