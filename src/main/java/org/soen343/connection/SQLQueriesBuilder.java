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

    public static String updateRole(String newRole, Integer idSelected){
        newRole = "'"+newRole+"'";
        return "UPDATE individuals SET role = " + newRole + " WHERE individualId = " + idSelected;
    }

    public static String getIndividuals(String username){
        username= "'"+username+"'";
        return "SELECT * FROM individuals WHERE username = " + username;
    }

    public static String updateIndividualName(String name, int idStelected){
        name = "'"+name+"'";
        return "UPDATE individuals SET name =" + name + " WHERE individualId = " + idStelected;
    }

    public static String addIndividual(String name, String role, String username){
        String individualInfo = "('" + name + "','" + role + "','" + username +"') ";
        return "INSERT INTO individuals (name,role,username) VALUES " + individualInfo;
    }

    public static String removeIndividual(int idRemove){
        return "DELETE from individuals WHERE individualId = " + idRemove;
    }
}
