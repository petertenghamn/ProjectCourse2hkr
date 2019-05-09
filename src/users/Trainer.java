package users;

import pokemon.PokemonMapper;
import java.util.ArrayList;
import java.util.Collections;

public class Trainer extends User {

    private String username;
    private int currency, winCount, lossCount;
    private ArrayList<PokemonMapper> collection, team;

    public Trainer(String email, String username, int currency, int winCount, int lossCount, PokemonMapper[] collection, PokemonMapper[] team) {
        super(email);
        this.username = username;
        this.currency = currency;
        this.winCount = winCount;
        this.lossCount = lossCount;

        this.collection = new ArrayList<PokemonMapper>();
        Collections.addAll(this.collection, collection);
        this.team = new ArrayList<PokemonMapper>();
        Collections.addAll(this.team, team);
    }

    public String getEmail(){
        return super.email;
    }

    public String getUsername(){
        return username;
    }

    public void setNewUserPassword(String password){
        super.setPassword(password);
    }

    public String getNewUserPassword(){
        return super.getPassword();
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLossCount() {
        return lossCount;
    }

    public void setLossCount(int lossCount) {
        this.lossCount = lossCount;
    }

    public ArrayList<PokemonMapper> getCollection() {
        return collection;
    }

    public void addToCollection(PokemonMapper newPokemon){
        collection.add(newPokemon);
    }

    public ArrayList<PokemonMapper> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<PokemonMapper> team) {
        this.team = team;
    }
}
