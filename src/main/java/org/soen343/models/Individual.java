package org.soen343.models;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import org.soen343.controller.IndividualController;


/**
 * The type Individual.
 */
public class Individual {

    private final int id;
    private String name, role, location, username;
    private ComboBox roleChoices, locationChoices;

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
        this.role= role;
        this.location = location;
        this.username = username;
    }

    /**
     * This method assigns a ComboBox to choose amongst
     * roles for this Individual.
     *
     * @param roles ObservableList of possible roles for individual.
     */
    public void setRoleChoices(ObservableList roles) {
        this.roleChoices = new ComboBox(roles);
        this.roleChoices.setValue(getRole());
        this.roleChoices.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            IndividualController.onEditRole(newValue, getId());
        });
    }

    /**
     * This method assigns a ComboBox to choose amongst
     * locations for this Individual.
     *
     * @param locations ObservableList of locations for individual.
     */
    public void setLocationChoices(ObservableList locations) {
        this.locationChoices = new ComboBox(locations);
        this.locationChoices.setValue(getLocation());
        this.locationChoices.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            IndividualController.onEditLocation(newValue, getId());
        });
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

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * Gets role choices.
     *
     * @return the role choices
     */
    public ComboBox getRoleChoices() {
        return roleChoices;
    }

    /**
     * Sets role choices.
     *
     * @param roleChoices the role choices
     */
    public void setRoleChoices(ComboBox roleChoices) {
        this.roleChoices = roleChoices;
    }

    /**
     * Gets location choices.
     *
     * @return the location choices
     */
    public ComboBox getLocationChoices() {
        return locationChoices;
    }

    /**
     * Sets location choices.
     *
     * @param locationChoices the location choices
     */
    public void setLocationChoices(ComboBox locationChoices) {
        this.locationChoices = locationChoices;
    }
}