package main;

import main.pokemon.Pokemon;
import main.pokemon.PokemonMapper;
import main.users.Professor;
import main.users.Trainer;
import main.users.User;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DatabaseLoader {

    //mysqlserver
    private String remoteUsername = "pokedb";
    private String remotePassword = "electrorat!";

    private String url = "jdbc:mysql://den1.mysql4.gear.host:3306/pokedb?user=" + remoteUsername + "&password=" + remotePassword;
    private Statement statement;

    private Connection con;
    private boolean connected;

    //encryption
    private static String key = "hkr12345group115"; // 128 bit key
    private static String salt = "ssshhhhhhhhhhh!!!!";
    private Key aesKey;
    private Cipher cipher;


    public DatabaseLoader(){
        // Create key and cipher
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            aesKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            cipher = Cipher.getInstance("AES");
        }
        catch (Exception e){
            System.out.println(e);
        }

        /*
        testing encryption
        String password = "12345";
        System.out.println(encryptPassword(password));
        */
    }

    /*
     * Try to make a connection to the database
     *
     * Result: set a boolean according to if you get access
     */
    private void connectToDB() {
        try {
            con = (Connection) DriverManager.getConnection(url);
            statement = con.createStatement();
            connected = true;
        } catch (SQLException ex) {
            //most likely need to set up Connector/j classpath
            //try removing and adding Connector/j if bug is occuring with connector/j already there
            System.out.println(ex.getMessage());
            connected = false;
        }
    }

    /*
     * Close the connection to the database
     */
    private void disconnectFromDB() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println("DB-Connection closing exception");
        }
    }

    /*
     * Load all of the pokemon stats from the database
     *
     * @return Pokemon[]
     */
    public ArrayList<Pokemon> loadAllPokemon() {
        //Load up all pokemon in the database
        connectToDB();

        if (connected) {
            ArrayList<Pokemon> pokemon = new ArrayList<>();

            try {
                ResultSet rs = statement.executeQuery("SELECT * FROM pokemon;");
                while (rs.next()) {
                    //int id, int health, int speed, int attack, int defense, String name, String type
                    pokemon.add(new Pokemon(rs.getInt(1), rs.getNString(2), rs.getInt(3),
                            rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(9), 
                            rs.getString(7) + ((rs.getString(8).isEmpty()) ? "" : (" and " + rs.getString(8)))));
                    System.out.println("Pokemon Loaded: {" + rs.getInt(1) + "} " + rs.getString(2));
                }
            } catch (SQLException ex) {
                System.out.println("Error executing the loadAllPokemon query!");
            }
            disconnectFromDB();

            return pokemon;
        }
        return null;
    }

    /*
     * Load all of the pokemon stats from the database
     *
     * @return Pokemon[]
     */
    public ArrayList<String> getTypeSelection(){
        ArrayList<String> types = new ArrayList<>();

        connectToDB();

        try{
            ResultSet rs = statement.executeQuery("SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'pokemon' AND COLUMN_NAME = 'first_type';");
            rs.next();
            //convert the query into array of strings
            

            System.out.println(rs.getString(1));

            //Array sqlTypes = rs.getArray("COLUMN_TYPE");
            //types = ((String[])sqlTypes.getArray());
        }
        catch(SQLException ex){
            System.out.println(ex);
            System.out.println("Error executing the getTypeSelection query!");
        }

        disconnectFromDB();

        return types;
    }


    /*
     * Add a new pokemon to the database
     */
    public void addPokemon(Pokemon pokemon){
        System.out.println("(TODO): PokeDB addPokemon doesn't add pokemon safely to the DB yet!");
        /*
        connectToDB();

        if (connected) {
            try {
                System.out.println("(TODO): PokeDB addPokemon doesn't add type correctly to the pokemon in DB yet!");
                statement.executeUpdate("INSERT INTO pokemon (pokemon_id, name, health, attack, defense, speed, first_type, cost) VALUES " +
                        "(" + pokemon.getIdTag() + ", '" + pokemon.getName() + "', " + pokemon.getHealth() + ", " +
                        pokemon.getAttack() + ", " + pokemon.getDefense() + ", " + pokemon.getSpeed() + ", '', " + pokemon.getCost() + ");");
            } catch (SQLException ex) {
                System.out.println("Error executing the query!");
            }
        }

        disconnectFromDB();
        */
    }

    /*
     * update a pokemon in the database
     */
    public void editPokemon(Pokemon pokemon){
        connectToDB();

        if (connected) {
            System.out.println("Havn't made the query yet! please implement!");
            /*
            try {
                ResultSet rs = statement.executeQuery("");
                while (rs.next()) {

                }
            } catch (SQLException ex) {
                System.out.println("Error executing the query!");
            }
            */
        }

        disconnectFromDB();
    }

    /*
     * remove a pokemon from the database
     */
    public void removePokemon(Pokemon pokemon){
        System.out.println("(TODO): PokeDB removePokemon doesn't remove pokemon safely from the DB yet!");
        /*
        connectToDB();

        if (connected) {
            try {
                statement.executeUpdate("DELETE FROM pokemon WHERE pokemon_id = " + pokemon.getIdTag() + ";");
            } catch (SQLException ex) {
                System.out.println("Error executing the query!");
            }
        }

        disconnectFromDB();
        */
    }

    /*
     * get the cost of the pokemon requested
     */
    public int getPokemonCost(int pokemonID) {
        connectToDB();

        int cost = 0;
        if (connected) {
            try {
                ResultSet rs = statement.executeQuery("SELECT cost FROM pokemon WHERE pokemon_id = " + pokemonID + ";");
                rs.next();
                cost = rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println("Error executing getPokemonCost query");
            }
        }

        disconnectFromDB();
        return cost;
    }

    /*
     * Get a list of all the trainers in the DB
     *
     * @return User[]
     */
    public ArrayList<User> getTrainers() {
        connectToDB();
        ArrayList<User> trainers = new ArrayList<>();

        if (connected) {
            try {
                ResultSet rs = statement.executeQuery("SELECT email, username, currency, win_count, loss_count FROM user_info INNER JOIN user ON email LIKE user_info_email AND is_professor = '0';");
                while (rs.next()) {
                    //set all info to the user and add him to the array of trainers
                    trainers.add(new Trainer(rs.getString(1), rs.getString(2), rs.getInt(3),
                            rs.getInt(4), rs.getInt(5), new ArrayList<PokemonMapper>(), new ArrayList<PokemonMapper>()));
                    //String email, String username, int currency, int winCount, int lossCount, PokemonMapper[] collection, PokemonMapper[] team
                }
            } catch (SQLException ex) {
                System.out.println("Error executing getTrainers query");
                System.out.println(ex);
            }

            for (User u : trainers){
                if (u instanceof Trainer){
                    String email = ((Trainer) u).getEmail();

                    //get the users collection
                    ArrayList<PokemonMapper> collection = getUserCollection(email);
                    ((Trainer) u).setCollection(collection);

                    //get the users team
                    ArrayList<PokemonMapper> team = getUserTeam(email);
                    ((Trainer) u).setTeam(team);
                }
            }
        }

        disconnectFromDB();
        return trainers;
    }

    /*
     * Get the collection of a User requested
     * Mainly used for professor since a Trainer gets their collection when logging in
     *
     * @return PokemonMapper[]
     */
    private ArrayList<PokemonMapper> getUserCollection(String email) {
        ArrayList<PokemonMapper> collection = new ArrayList<>();

        if (connected) {
            try {
                //get the players id from the user table belonging to the email, this will be used to query other tables
                ResultSet userInfo = statement.executeQuery("SELECT user_id FROM user WHERE user_info_email LIKE '" + email + "';");
                userInfo.next();
                int userID = userInfo.getInt(1);

                ResultSet rs = statement.executeQuery("SELECT pokemon_id, nickname FROM collection WHERE user_id LIKE '" + userID + "';");
                while (rs.next()) {
                    collection.add(new PokemonMapper(rs.getInt(1), rs.getString(2)));
                }
            } catch (SQLException ex) {
                System.out.println("Error executing getUserCollection query!");
                System.out.println(ex);
            }
        }

        return collection;
    }

    /*
     * Get the team of a User requested
     * trainer can get the team of an opponent here, or a professor can get it to inspect
     *
     * @return PokemonMapper[]
     */
    private ArrayList<PokemonMapper> getUserTeam(String email) {
        ArrayList<PokemonMapper> team = new ArrayList<>();

        if (connected) {
            try {
                //get the players id from the user table belonging to the email, this will be used to query other tables
                ResultSet userInfo = statement.executeQuery("SELECT user_id FROM user WHERE user_info_email LIKE '" + email + "';");
                userInfo.next();
                int userID = userInfo.getInt(1);

                ResultSet rs = statement.executeQuery("SELECT collection.user_id, pokemon_id, nickname FROM collection INNER JOIN user_has_team WHERE nickname LIKE collection_nickname AND collection.user_id = user_has_team.user_id;");
                while (rs.next()) {
                    if (rs.getInt(1) == userID) {
                        team.add(new PokemonMapper(rs.getInt(2), rs.getString(3)));
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error executing getUserTeam query");
                System.out.println(ex);
            }
        }

        return team;
    }

    /*
     * Check if it has been 24 hours since the player received their bonus reward
     * Give a reward if it is due, and return true so that the screen knows to tell the player that they received a reward
     *
     * @return boolean
     */
    public boolean loginBonusCheck(String email) {
        connectToDB();

        if (connected) {
            try {
                ResultSet userInfo = statement.executeQuery("SELECT login_bonus FROM user_info WHERE email LIKE '" + email + "';");
                userInfo.next();
                java.util.Date lastBonus = userInfo.getDate(1);
                Date yesterday = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));

                //check to see if last bonus was received more than 24 hours ago
                if (lastBonus.before(yesterday)) {
                    try {
                        statement.executeUpdate("UPDATE user_info SET login_bonus = curdate() WHERE email LIKE '" + email + "';");
                    } catch(SQLException ex){
                        System.out.println("Error executing login update!");
                        System.out.println(ex);
                    }
                    disconnectFromDB();
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println("Error executing login bonus check!");
                System.out.println(ex);
            }
        }

        disconnectFromDB();
        return false;
    }

    /*
     * Verify the user by comparing entered information to information in the database
     *
     * @return User
     */
    public User authenticateLogin(String email, String password) {
        connectToDB();

        if (connected) {
            //print out all user names, just there for testing, doesn't need to print in full version
            try {
                ResultSet rs = statement.executeQuery("select email from user_info;");

                //print out all the emails available to select from
                System.out.println();
                System.out.println("--- Users in DB ---");
                while (rs.next()) {
                    System.out.println("user name: " + rs.getString(1));
                }
                System.out.println("--- ----------- ---");
                System.out.println();

                rs = statement.executeQuery("select is_professor, email, password from user, user_info where user.user_info_email like user_info.email;");

                while (rs.next()) {
                    if (rs.getString(2).equals(email)) {
                        if (decryptPassword(rs.getString(3)).equals(password)) {
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

    /*
     * Retrieve all information related to the user requested
     *
     * @return User
     */
    private User getUserInfo(boolean isProfessor, String email) {
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
                userInfo = statement.executeQuery("SELECT username, currency, win_count, loss_count FROM user_info WHERE email LIKE '" + email + "';");
                userInfo.next();
                String username = userInfo.getString(1);
                int currency = userInfo.getInt(2);
                int wins = userInfo.getInt(3);
                int losses = userInfo.getInt(4);

                //get the collection the player owns
                ArrayList<PokemonMapper> collection = new ArrayList<>();
                try {
                    ResultSet userCollection = statement.executeQuery("SELECT pokemon_id, nickname FROM collection WHERE user_id = " + userID + ";");
                    while (userCollection.next()) {
                        PokemonMapper pokeID = new PokemonMapper(userCollection.getInt(1), userCollection.getString(2));
                        collection.add(pokeID);
                    }
                } catch (Exception e) {
                    //result set may be empty if error occurred here
                    System.out.println("Error executing collection query");
                }

                //get the team the player has selected
                ArrayList<PokemonMapper> team = new ArrayList<>();
                try {
                    ResultSet userTeam = statement.executeQuery("SELECT pokemon_id, nickname FROM collection INNER JOIN user_has_team ON " +
                            "collection.user_id LIKE " + userID + " AND " +
                            "collection.user_id LIKE user_has_team.user_id AND " +
                            "collection.nickname LIKE user_has_team.collection_nickname;");
                    while (userTeam.next()) {
                        PokemonMapper pokeID = new PokemonMapper(userTeam.getInt(1), userTeam.getString(2));
                        team.add(pokeID);
                    }
                } catch (Exception e) {
                    //result set may be empty if error occurred here
                    System.out.println("Error executing user_has_team query");
                }

                loginUser = new Trainer(email, username, currency, wins, losses, collection, team);
            }
            return loginUser;

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    /*
     * Check if the email is being used in the database
     *
     * @return Bool
     */
    public boolean checkIfEmailAvailable(String email) {
        connectToDB();

        if (connected) {
            try {
                ResultSet rs = statement.executeQuery("SELECT user_info_email FROM user;");
                while (rs.next()) {
                    if (rs.getString(1).equals(email)) {
                        disconnectFromDB();
                        return false;
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error executing the query!");
            }

            disconnectFromDB();
        }
        return true;
    }

    /*
     * Insert the new users information into the database
     */
    public void createNewUser(User user) {
        connectToDB();

        if (connected) {
            if (user instanceof Trainer) {
                //insert all of the data in the user class into the database as a new user
                try {
                    statement.executeUpdate("INSERT INTO user_info (email, password, username, currency, login_bonus, win_count, loss_count) VALUES" +
                            "('" + ((Trainer) user).getEmail() + "', '" +
                            encryptPassword(((Trainer) user).getNewUserPassword()) + "', '" +
                            ((Trainer) user).getUsername() + "', " +
                            ((Trainer) user).getCurrency() + ", " +
                            "curdate(), " +
                            ((Trainer) user).getWinCount() + ", " +
                            ((Trainer) user).getLossCount() + ");");
                } catch (SQLException ex) {
                    System.out.println("Error executing the insert into user_info!");
                    System.out.println(ex);
                }
                //create a user with the email to give the user a user_id to relate to all the other tables
                try {
                    statement.executeUpdate("INSERT INTO user (user_info_email) VALUES" +
                            "('" + ((Trainer) user).getEmail() + "');");
                } catch (SQLException ex) {
                    System.out.println("Error executing the insert into user!");
                    System.out.println(ex);
                }
            }
        }

        disconnectFromDB();
    }

    /*
     * Update the users Currency
     */
    public void updateUserCurrency(User user){
        connectToDB();

        if (connected){
            if (user instanceof Trainer){
                try {
                    //get the players id from the user table belonging to the email, this will be used to query other tables
                    statement.executeUpdate("UPDATE user_info SET currency = " + ((Trainer) user).getCurrency() +
                            " WHERE email like '" + ((Trainer) user).getEmail() + "';");
                } catch (SQLException ex) {
                    System.out.println("Error executing updateUserCurrency query!");
                    System.out.println(ex);
                }
            }
        }

        disconnectFromDB();
    }

    /*
     * Update the users Score
     */
    public void updateUserScore(User user){
        connectToDB();

        if (connected){
            if (user instanceof Trainer){
                try {
                    //get the players id from the user table belonging to the email, this will be used to query other tables
                    statement.executeUpdate("UPDATE user_info SET " +
                                    "win_count = " + ((Trainer) user).getWinCount() + ", " +
                                    "loss_count = " + ((Trainer) user).getLossCount() + " " +
                                    "WHERE email LIKE '" + ((Trainer) user).getEmail() + "';");
                } catch (SQLException ex) {
                    System.out.println("Error executing updateUserScore query!");
                    System.out.println(ex);
                }
            }
        }

        disconnectFromDB();
    }

    /*
     * Add pokemon to the users collection
     */
    public void addPokemonUserCollection(User user, PokemonMapper pokemon){
        connectToDB();

        if (connected){
            if (user instanceof Trainer){
                try {
                    statement.executeUpdate("INSERT INTO collection (nickname, user_id, pokemon_id) VALUES " +
                            "('" + pokemon.getNickname() + "', " +
                            "(SELECT user_id FROM user WHERE user_info_email LIKE '" + ((Trainer) user).getEmail() + "'), " +
                            pokemon.getId() + ");");
                } catch (SQLException ex) {
                    System.out.println("Error executing addPokemonUserCollection query!");
                    System.out.println(ex);
                }
            }
        }

        disconnectFromDB();
    }

    /*
     * Add pokemon to the users team
     */
    public void addPokemonUserTeam(User user, PokemonMapper pokemon){
        connectToDB();

        if (connected){
            if (user instanceof Trainer){
                try {
                    statement.executeUpdate("INSERT INTO user_has_team (collection_nickname, user_id) VALUES" +
                            " ((SELECT nickname FROM collection WHERE nickname LIKE '" + pokemon.getNickname() + "')," +
                            " (SELECT user_id FROM user WHERE user_info_email LIKE '" + ((Trainer) user).getEmail() + "'));");
                } catch (SQLException ex) {
                    System.out.println("Error executing addPokemonUserTeam query!");
                    System.out.println(ex);
                }
            }
        }

        disconnectFromDB();
    }

    /*
     * Update pokemon from the users team
     */
    public void exchangePokemonUserTeam(User user, PokemonMapper pokemon, PokemonMapper oldPokemon){
        connectToDB();

        if (connected){
            if (user instanceof Trainer){
                try {
                    statement.executeUpdate("UPDATE user_has_team SET collection_nickname = '" + pokemon.getNickname() + "'" +
                            " WHERE collection_nickname LIKE '" + oldPokemon.getNickname() + "' AND" +
                            " user_id = (SELECT user_id FROM user WHERE user_info_email LIKE '" + ((Trainer) user).getEmail() + "');");
                } catch (SQLException ex) {
                    System.out.println("Error executing exchangePokemonUserTeam query!");
                    System.out.println(ex);
                }
            }
        }

        disconnectFromDB();
    }

    /*
     * Remove pokemon from the users team
     */
    public void removePokemonUserTeam(User user, PokemonMapper pokemon){
        connectToDB();

        if (connected){
            if (user instanceof Trainer){
                try {
                    statement.executeUpdate("DELETE FROM user_has_team WHERE" +
                            " user_id = (SELECT user_id FROM user WHERE user_info_email LIKE '" + ((Trainer) user).getEmail() + "') AND" +
                            " collection_nickname LIKE '" + pokemon.getNickname() + "';");
                } catch (SQLException ex) {
                    System.out.println("Error executing removePokemonUserTeam query!");
                    System.out.println(ex);
                }
            }
        }

        disconnectFromDB();
    }

    private String encryptPassword(String password){
        try
        {
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b: encrypted) {
                sb.append((char)b);
            }

            // the encrypted String
            password = sb.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return password;
    }

    private String decryptPassword(String password){
        try {
            // now convert the string to byte array
            // for decryption
            byte[] bb = new byte[password.length()];
            for (int i=0; i<password.length(); i++) {
                bb[i] = (byte) password.charAt(i);
            }

            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            password = new String(cipher.doFinal(bb));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return password;
    }
}
