package org.soen343.models;

import org.soen343.models.house.Individual;

public class User {

    private static String username;
    private static Individual currentIndividual;

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
