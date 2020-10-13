package org.soen343.models;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import org.soen343.controller.IndividualController;


public class Individual {

    private final int id;
    private String name, role, location, username;
    private ComboBox roleChoices, locationChoices;

    /**
     * Creates an Individual
     *
     * @param id   id of the individual
     * @param name name of the individual
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
     * @param roles
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
     * @param locations
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


    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ComboBox getRoleChoices() {
        return roleChoices;
    }

    public void setRoleChoices(ComboBox roleChoices) {
        this.roleChoices = roleChoices;
    }

    public ComboBox getLocationChoices() {
        return locationChoices;
    }

    public void setLocationChoices(ComboBox locationChoices) {
        this.locationChoices = locationChoices;
    }
}