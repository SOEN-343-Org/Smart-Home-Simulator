package org.soen343.models;

public class User {

    public static String username;
    public static Individual currentIndividual;

    /**
     * Get username
     *
     * @return String username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Set username
     *
     * @param name
     */
    public static void setUsername(String name) {
        username = name;
    }

    /**
     * Get current individual
     *
     * @return Individual currentIndividual
     */
    public static Individual getCurrentIndividual() {
        return currentIndividual;
    }

    /**
     * Set current individual
     *
     * @param individual
     */
    public static void setCurrentIndividual(Individual individual) {
        currentIndividual = individual;
    }
}
