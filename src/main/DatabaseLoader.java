package main;

import pokemon.Pokemon;
import users.Professor;
import users.Trainer;
import users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseLoader {

    private String url = "jdbc:mysql://127.0.0.1:3306/pokedb?user=root&password=root";
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
            System.out.println("DB-Connection failed");
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

    public void testFunction(){
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println("Test Function to test the DB-Loader + query");

        connectToDB();

        if (connected) {
            //create a pokemon to print
            try {
                statement.executeUpdate("INSERT into pokemon values (999, 'ElectroRat', 9001, 999, 999, 1000);");
            } catch (SQLException ex) {
                System.out.println("Error executing the update - insert!");
            }

            //print out test pokemon
            try {
                ResultSet rs = statement.executeQuery("SELECT name FROM pokemon;");

                while (rs.next()) {
                    System.out.println("pokemon name: " + rs.getString(1));
                }
            } catch (SQLException ex) {
                System.out.println("Error executing the query!");
            }

            //remove pokemon made
            try {
                statement.executeUpdate("Delete from pokemon where pokemon_id = 999;");
            } catch (SQLException ex) {
                System.out.println("Error executing the update - delete!");
            }

            disconnectFromDB();
        }

        System.out.println("---------- End of Test Function! ----------");
        System.out.println("-------------------------------------------");
        System.out.println();
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
                    pokemon.add(new Pokemon(rs.getInt(1), rs.getInt(3), rs.getInt(6),
                            rs.getInt(4), rs.getInt(5), rs.getNString(2), "Working on types"));
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
                ResultSet rs = statement.executeQuery("SELECT * FROM user;");

                while (rs.next()) {
                    System.out.println("user name: " + rs.getString(2));
                    if (rs.getString(2).equals(email)) {
                        if (rs.getString(3).equals(password)) {
                            //load all information pertaining to user
                            User loginUser;
                            System.out.println("  ---> User/Pass: Matched!");
                            if (rs.getBoolean(4)) {
                                //is_professor
                                loginUser = new Professor(email);
                            } else {
                                //retrieve all of the users stats
                                int userID = rs.getInt(1);
                                ResultSet userStats = statement.executeQuery("SELECT * FROM user_stats WHERE user_id = " + userID + ";");
                                int wins = 0;
                                int losses = 0;
                                try {
                                    wins = userStats.getInt(3);
                                    losses = userStats.getInt(4);
                                } catch (Exception e) {
                                    System.out.println("Empty stats");
                                }

                                int[] collection = new int[0];
                                try {
                                    ResultSet userCollection = statement.executeQuery("SELECT * FROM user_collection WHERE user_id = " + userID + ";");
                                    Array c = userCollection.getArray(2);
                                    collection = (int[]) c.getArray();
                                } catch (Exception e) {
                                    //result set may be empty if error occurred here
                                    System.out.println("Error executing user_collection query");
                                }

                                int[] team = new int[0];
                                try {
                                    ResultSet userTeam = statement.executeQuery("SELECT * FROM user_has_team WHERE user_id = " + userID + ";");
                                    Array t = userTeam.getArray(3);
                                    team = (int[]) t.getArray();
                                } catch (Exception e) {
                                    //result set may be empty if error occurred here
                                    System.out.println("Error executing user_has_team query");
                                }

                                loginUser = new Trainer(email, 0, wins, losses, collection, team);
                            }
                            return loginUser;
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

    public boolean checkIfEmailAvailable(String email) {
        connectToDB();

        if (connected) {
            try {
                ResultSet rs = statement.executeQuery("SELECT email FROM user;");
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
                    statement.executeUpdate("INSERT into user (email, password) values" +
                            "('" + ((Trainer) user).getEmail() + "', '" + ((Trainer) user).getNewUserPassword() + "');");
                }
            } catch (SQLException ex) {
                System.out.println("Error executing the update - insert!");
            }

            disconnectFromDB();
        }
    }
}
