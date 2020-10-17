package org.soen343.models;

/**
 * The type Individual.
 */
public class Individual {

    private final int id;
    private String name, role, location, username;

    /**
     * Creates an Individual
     *
     * @param id       Integer id of the individual
     * @param name     String name of the individual
     * @param role     String role of the individual
     * @param location String location of the individual
     * @param username String username of the User who created the individual
     */
    public Individual(int id, String name, String role, String location, String username) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.location = location;
        this.username = username;
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

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}