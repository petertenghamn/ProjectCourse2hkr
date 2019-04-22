package Main;

import java.sql.*;

public class DatabaseLoader {

    String url = "jdbc:mysql://127.0.0.1:3306/pokedb?user=root&password=root";
    Statement statement;

    private Connection con;
    private boolean connected;

    public void TestFunction(){
        try {
            con = (Connection) DriverManager.getConnection(url);
            statement = con.createStatement();
            connected = true;
        } catch (SQLException ex){
            //most likely need to set up Connector/j classpath
            System.out.println("DB-Connection failed");
            connected = false;
        }

        if (connected) {
            //create a pokemon to print
            try {
                statement.executeUpdate("INSERT into pokemon values (1, 'ElectroRat', 9001, 999, 999, 1000);");
            } catch (SQLException ex) {
                System.out.println("Error executing the update - insert!");
            }

            //print out test pokemon
            try {
                ResultSet rs = statement.executeQuery("SELECT name FROM pokemon;");

                while (rs.next()) {
                    System.out.println("Pokemon name: " + rs.getString(1));
                }
            } catch (SQLException ex) {
                System.out.println("Error executing the query!");
            }

            //remove pokemon made
            try {
                statement.executeUpdate("Delete from pokemon where pokemon_id = 1;");
            } catch (SQLException ex) {
                System.out.println("Error executing the update - delete!");
            }

            if (connected){
                try {
                    con.close();
                }
                catch (Exception e){
                    System.out.println("DB-Connection closing exception");
                }
            }
        }
    }
}
