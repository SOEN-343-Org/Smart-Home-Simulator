package org.soen343.models;

public class Individual {

    private final int id;
    private final String name;

    /**
     * Creates an Individual
     *
     * @param id   id of the individual
     * @param name name of the individual
     */
    public Individual(int id, String name) {
        this.id = id;
        this.name = name;
        //TODO: Add role
    }

    /**
     * Gets the id of the individual
     *
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the individual
     *
     * @return string name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Individual " + getName() + ", role = ";
    }
}
