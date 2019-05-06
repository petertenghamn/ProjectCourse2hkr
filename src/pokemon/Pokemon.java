package pokemon;

public class Pokemon {

    private int idTag, health, speed, attack, defense;
    private String name, type;

    // Constructor calls the "GenerateTag" Method in order to make sure it's a unique tag
    public Pokemon(int id, String name, int health, int attack, int defense, int speed, String type) {
        idTag = id;
        this.health = health;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
        this.name = name;
        this.type = type;
    }
    /*
    // Generate tag is used by the constructor to give a unique tag to each object.
    private void generateTag(Pokemon pokemon){
        for(int gt = 0; gt <= idArray.size(); gt++){
            if (idArray.get(gt) == null){
                pokemon.idTag = gt;
            }
        }
    }
    */

    // Getter Methods After this Comment

    public int getIdTag() {
        return idTag;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
