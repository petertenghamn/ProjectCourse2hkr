package main;

import pokemon.Pokemon;
import pokemon.PokemonMapper;
import users.Professor;
import users.Trainer;
import users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseLoader {

    private String url = "jdbc:mysql://localhost:3306/pokedb?user=root&password=root?serverTimezone=UTF-8";
    private Statement statement;

    private Connection con;
    private boolean connected;

    private void connectToDB(){
        try {
            con = (Connection) DriverManager.getConnection(url);
            statement = con.createStatement();
            connected = true;
        } catch (SQLException ex){
            //most likely need to set up Connector/j classpath
            //try removing and adding Connector/j if bug is occuring with connector/j already there
            System.out.println(ex.getMessage());
            connected = false;
        }
    }
    private void disconnectFromDB(){
        try {
            con.close();
        } catch (Exception e) {
            System.out.println("DB-Connection closing exception");
        }
    }

    public Pokemon[] loadAllPokemon(){
        //Load up all pokemon in the database
        connectToDB();

        if (connected) {
            List<Pokemon> pokemon = new ArrayList<>();

            try {
                ResultSet rs = statement.executeQuery("SELECT * FROM pokemon;");
                while (rs.next()) {
                    //int id, int health, int speed, int attack, int defense, String name, String type
                    pokemon.add(new Pokemon(rs.getInt(1), rs.getNString(2), rs.getInt(3),
                            rs.getInt(4), rs.getInt(5), rs.getInt(6), "Working on types"));
                    System.out.println("Pokemon Loaded: " + rs.getString(2));
                }
            } catch (SQLException ex) {
                System.out.println("Error executing the query!");
            }
            disconnectFromDB();

            return pokemon.toArray(new Pokemon[pokemon.size()]);
        }
        return null;
    }

    public User authenticateLogin(String email, String password) {
        connectToDB();

        if (connected) {
            //print out all user names, just there for testing, doesn't need to print in full version
            try {
                ResultSet rs = statement.executeQuery("select email from user_info;");

                //print out all the emails available to select from
                System.out.println();
                System.out.println("--- Users in DB ---");
                while (rs.next()){
                    System.out.println("user name: " + rs.getString(1));
                }
                System.out.println("--- ----------- ---");
                System.out.println();

                rs = statement.executeQuery("select is_professor, email, password from user, user_info where user.user_info_email like user_info.email;");

                while (rs.next()) {
                    if (rs.getString(2).equals(email)) {
                        if (rs.getString(3).equals(password)) {
                            return getUserInfo(rs.getBoolean(1), email);
                        } else {
                            disconnectFromDB();
                            //username matched but password didn't, can return early
                            return null;
                        }
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error executing the query!");
            }

            disconnectFromDB();
        }
        return null; //login information given was wrong so return null
    }

    private User getUserInfo(boolean isProfessor ,String email) {
        //load all information pertaining to user
        User loginUser;

        try {
            if (isProfessor) {
                loginUser = new Professor(email);
            } else {
                //get the players id from the user table belonging to the email, this will be used to query other tables
                ResultSet userInfo = statement.executeQuery("SELECT user_id FROM user WHERE user_info_email LIKE '" + email + "';");
                userInfo.next();
                int userID = userInfo.getInt(1);
                //get the players info from the user_info table belonging to the email
                userInfo = statement.executeQuery("SELECT username, win_count, loss_count FROM user_info WHERE email LIKE '" + email + "';");
                userInfo.next();
                String username = userInfo.getString(1);
                int wins = userInfo.getInt(2);
                int losses = userInfo.getInt(3);

                //get the collection the player owns
                ArrayList<PokemonMapper> collection = new ArrayList<>();
                try {
                    ResultSet userCollection = statement.executeQuery("SELECT pokemon_id, nickname FROM user_collection WHERE user_id = " + userID + ";");
                    while (userCollection.next()){
                        PokemonMapper pokeID = new PokemonMapper(userCollection.getInt(1), userCollection.getString(2));
                        collection.add(pokeID);
                    }
                } catch (Exception e) {
                    //result set may be empty if error occurred here
                    System.out.println("Error executing user_collection query");
                }

                //get the team the player has selected
                ArrayList<PokemonMapper> team = new ArrayList<>();
                try {
                    ResultSet userTeam = statement.executeQuery("select user_collection.pokemon_id, nickname from user_has_team, user_collection where user_has_team.user_id = user_collection.user_id and user_collection.user_id = " + userID + ";");
                    while (userTeam.next()){
                        PokemonMapper pokeID = new PokemonMapper(userTeam.getInt(1), userTeam.getString(2));
                        team.add(pokeID);
                    }
                } catch (Exception e) {
                    //result set may be empty if error occurred here
                    System.out.println("Error executing user_has_team query");
                }

                //convert array lists to arrays
                PokemonMapper[] collectionArray = collection.toArray(new PokemonMapper[collection.size()]);
                PokemonMapper[] teamArray = collection.toArray(new PokemonMapper[team.size()]);

                loginUser = new Trainer(email, username, 0, wins, losses, collectionArray, teamArray);
            }
            return loginUser;

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public boolean checkIfEmailAvailable(String email) {
        connectToDB();

        if (connected) {
            try {
                ResultSet rs = statement.executeQuery("SELECT user_info_email FROM user;");
                while (rs.next()) {
                    if (rs.getString(1).equalsIgnoreCase(email)){
                        disconnectFromDB();
                        return false;
                    }
                }
            }
            catch (SQLException ex) {
                System.out.println("Error executing the query!");
            }

            disconnectFromDB();
        }
        return true;
    }

    public void createNewUser(User user) {
        connectToDB();

        if (connected) {
            try {
                if (user instanceof Trainer) {
                    //insert all of the data in the user class into the database as a new user
                    statement.executeUpdate("INSERT INTO user_info (email, password, username, login_bonus, win_count, loss_count) VALUES" +
                            "('" + ((Trainer) user).getEmail() + "', '" +
                            ((Trainer) user).getNewUserPassword() + "', '" +
                            ((Trainer) user).getUsername() + "', '" +
                            "curdate()" + "', '" +
                            ((Trainer) user).getWinCount() + "', '" +
                            ((Trainer) user).getLossCount() + "');");
                    //create a user with the email to give the user a user_id to relate to all the other tables
                    statement.executeUpdate("INSERT INTO user (email) VALUES" +
                            "('" + ((Trainer) user).getEmail() + "');");
                    //create a collection for the trainer in the database
                    PokemonMapper[] collection = ((Trainer) user).getCollection();
                    for (PokemonMapper p : collection) {
                        statement.executeUpdate("INSERT INTO user_collection (user_id, pokemon_id, nickname) VALUES " +
                                "((SELECT user_id FROM user WHERE user_info_email LIKE " + ((Trainer) user).getEmail() + "), " +
                                p.getId() + ", '" +
                                p.getNickname() + "');");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error executing the update - insert!");
            }

            disconnectFromDB();
        }
    }
}
