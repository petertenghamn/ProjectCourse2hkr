package main.users;

public class Professor extends User {

    public Professor(String email){
        super(email);
    }

    public String getEmail(){
        return super.email;
    }
}
