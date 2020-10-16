package org.soen343.connection;

public class SQLQueriesBuilder {

    /**
     * Querie that returns 1 if user exists and 0 if it does not
     * @param username of the user
     * @return querie
     */
    public static String usernameExists(String username){
        username = "'"+username+"'";
        return "SELECT count(1) FROM users WHERE username = " + username;
    }

    public static String userExists(String username, String password){
        username = "'"+username+"'";
        password = "'"+password+"'";
        return "SELECT count(1) FROM users WHERE username = " + username + " AND password = " + password;
    }

    public static String addUser(String username, String password){
        username = "'"+username+"'";
        password = "'"+password+"'";
        return "INSERT INTO users VALUES(" + username + "," + password + ")";
    }
}
