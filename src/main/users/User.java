package main.users;

public abstract class User {
    protected String email;
    private String password;

    public User(String email){
        this.email = email;
    }

    protected void setPassword(String password){
        this.password = password;
    }

    protected String getPassword(){
        return password;
    }
}
