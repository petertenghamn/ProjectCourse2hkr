package users;

import pokemon.Pokemon;

public class Trainer extends User {

    private int currency;
    private int winCount;
    private int loseCount;
    private Pokemon[] collection;
    private Pokemon[] team;


    public Trainer(int currency, int winCount, int loseCount, Pokemon[] collection, Pokemon[] team) {
        this.currency = currency;
        this.winCount = winCount;
        this.loseCount = loseCount;
        this.collection = collection;
        this.team = team;
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

    public void setCollection(Pokemon[] collection) {
        this.collection = collection;
    }

    public Pokemon[] getTeam() {
        return team;
    }

    public void setTeam(Pokemon[] team) {
        this.team = team;
    }
}
