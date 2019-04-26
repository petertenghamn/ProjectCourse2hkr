package users;

import pokemon.Pokemon;

public class Trainer extends User {

    private int currency;
    private int winCount;
    private int loseCount;
    private Pokemon[] collection;
    private Pokemon[] team;

    public Trainer(String email, int currency, int winCount, int loseCount, Pokemon[] collection, Pokemon[] team) {
        super(email);
        this.currency = currency;
        this.winCount = winCount;
        this.loseCount = loseCount;
        this.collection = collection;
        this.team = team;
    }

    public String getEmail(){
        return super.email;
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

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public Pokemon[] getCollection() {
        return collection;
    }

    public void addToCollection(Pokemon newPokemon){
        Pokemon[] newCollection = new Pokemon[collection.length + 1];
        for (int i = 0; i < collection.length; i++){
            newCollection[i] = collection[i];
        }
        newCollection[newCollection.length - 1] = newPokemon;
    }

    public Pokemon[] getTeam() {
        return team;
    }

    public void setTeam(Pokemon[] team) {
        this.team = team;
    }
}
