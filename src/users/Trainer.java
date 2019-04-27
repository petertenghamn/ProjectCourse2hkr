package users;

public class Trainer extends User {

    private int currency;
    private int winCount;
    private int loseCount;
    private int[] collectionIDs;
    private int[] teamIDs;

    public Trainer(String email, int currency, int winCount, int loseCount, int[] collectionIDs, int[] teamIDs) {
        super(email);
        this.currency = currency;
        this.winCount = winCount;
        this.loseCount = loseCount;
        this.collectionIDs = collectionIDs;
        this.teamIDs = teamIDs;
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

    public int[] getCollectionIDs() {
        return collectionIDs;
    }

    public void addToCollectionIDs(int newPokemonID){
        int[] newCollection = new int[collectionIDs.length + 1];
        for (int i = 0; i < collectionIDs.length; i++){
            newCollection[i] = collectionIDs[i];
        }
        newCollection[newCollection.length - 1] = newPokemonID;
    }

    public int[] getTeam() {
        return teamIDs;
    }

    public void setTeamIDs(int[] teamIDs) {
        this.teamIDs = teamIDs;
    }
}
