package main.pokemon;

public class PokemonMapper {

    private int id;
    private String nickname;

    public PokemonMapper(int id, String nickname){
        this.id = id;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
