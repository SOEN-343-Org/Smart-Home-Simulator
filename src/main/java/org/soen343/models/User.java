package org.soen343.models;

public class User {

    public static String username;
    public static Individual currentIndividual;


    public static String getUsername() {
        return username;
    }

    public static void setUsername(String name) {
        username = name;
    }

    public static Individual getCurrentIndividual() {
        return currentIndividual;
    }

    public static void setCurrentIndividual(Individual individual) {
        currentIndividual = individual;
    }
}
