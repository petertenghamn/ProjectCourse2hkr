package pokemon;

public class Pokemon {

    private int idTag, health, speed, attack, defense;
    private String name, type;

    public Pokemon(int id, String name, int health, int attack, int defense, int speed, String type) {
        idTag = id;
        this.health = health;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
        this.name = name;
        this.type = type;
    }

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
