package Main;

import java.sql.*;

public class DatabaseLoader {

    String url = "jdbc:mysql://127.0.0.1:3306/pokedb?user=root&password=root";
    Statement statement;

    private boolean connected;

    public DatabaseLoader(){
        try {
            Connection c = DriverManager.getConnection(url);
            statement = c.createStatement();
            connected = true;
        } catch (SQLException ex){
            //most likely need to set up Connector/j classpath
            System.out.println("DB-Connection failed");
            connected = false;
        }
    }

    public void TestFunction(){
        if (connected) {
            try {
                ResultSet rs = statement.executeQuery("SELECT name FROM pokemon");

                while (rs.next()) {
                    System.out.println("Pokemon name: " + rs.getString(1));
                }
            } catch (SQLException ex) {
                System.out.println("Error executing the query!");
            }
        }
    }
}
