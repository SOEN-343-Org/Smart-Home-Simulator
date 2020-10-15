package org.soen343.models;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.soen343.controller.IndividualController;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The type Individual.
 */
public class Individual implements Reporter {

    public final int id;
    public String name, role, location, username;
    private ComboBox roleChoices, locationChoices;
    private ArrayList<Observer> locationObserversList = new ArrayList<Observer>();

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
            IndividualController.onEditRole(newValue, getId(), this);
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
            IndividualController.onEditLocation(newValue, getId(), this);
        });
    }

    /**
     * This method gets the elements from the individuals table in the database
     * @param username String username associated to the individual
     * @param dbCon Connection instance to the database
     * @return ResultTest of individuals
     */
    public static ResultSet getIndividualsDB(String username, Connection dbCon) {
        ResultSet rs = null;
        try {
             rs = dbCon.createStatement().executeQuery("select * from individuals where username='" + username + "'");
        } catch (SQLException e) {
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
        }
        return rs;
    }

    /**
     * This method updates the individual's name in the database
     * @param dbCon Connection instance to the database
     * @param dbTableName String name of table affected in the database
     * @param newName String desired name for the individual
     * @param idSelected Integer ID of the individual
     */
    public static void updateNameDB(Connection dbCon, String dbTableName, String newName, Integer idSelected) {
        try {
            dbCon.createStatement().executeUpdate(
                    "update "+ dbTableName +
                            " set name = '"+ newName +
                            "' where individualId = " + idSelected);
        } catch (SQLException e) {
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This method updates the role of the individual in the database
     * @param dbCon Connection instance to the database
     * @param dbTableName String name of table affected in the database
     * @param newRole String desired role for the individual
     * @param idSelected Integer ID of the individual
     */
    public static void updateRoleDB(Connection dbCon, String dbTableName, String newRole, Integer idSelected) {
        try {
            dbCon.createStatement().executeUpdate(
                    "UPDATE "+ dbTableName +
                            " SET role = '"+ newRole +
                            "' WHERE individualId = " + idSelected);
        } catch (SQLException e) {
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This method removes an individual from the database
     * @param dbCon Connection instance to the database
     * @param dbTableName String name of table affected in the database
     * @param idToRemove TextField ID of the individual
     */
    public static void removeIndividualDB(Connection dbCon, String dbTableName, TextField idToRemove) {
            try {
                dbCon.createStatement().executeUpdate("DELETE from "+ dbTableName +
                        " where individualId="+idToRemove.getText());
            } catch (SQLException e) {
                Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
            }
    }

    /**
     * This method adds an individual to the database
     * @param dbCon Connection instance to the database
     * @param dbTableName String name of table affected in the database
     * @param addedName String desired name for the individual
     * @param roleChoices ChoiceBox options for roles
     * @param username String username associated to the individual
     */
    public static Integer addIndividualDB(Connection dbCon, String dbTableName, TextField addedName,
                                       ChoiceBox roleChoices , String username) {
        int generatedKey = 0;
            try {
                String sqlStatement = "INSERT into "+ dbTableName +
                        " (name,role,username) VALUES ('"+ addedName.getText() + "','" +
                        roleChoices.getSelectionModel().getSelectedItem() + "','" +
                         "test123" + "')";
                PreparedStatement ps = dbCon.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    generatedKey = rs.getInt(1);
                }

                System.out.println("Inserted record's ID : " + generatedKey);
            } catch (SQLException e) {
                Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
            }
            return generatedKey;
    }

    /**
     * This method sets the registers all the location
     * observers to observe this individual.
     * @param locationsList
     */
    public void setLocationObserversList(ArrayList<Room> locationsList) {
        for (Room r : locationsList) {
            registerObserver(r);
        }
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
        return "Individual " + getName() + ", role = " + getRole();
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


    /**
     * This method registers a specific observer
     * to this individual.
     * It first adds the observer to the individual's unique list of location observers
     * then the observer updates their unique list of individuals
     * @param newRoomObserver
     */
    @Override
    public void registerObserver(Observer newRoomObserver) {

        locationObserversList.add(newRoomObserver);

        newRoomObserver.update(this);
    }

    /**
     * This method unregisters an observer from the
     * individual's list of observers.
     * @param deleteObserver
     */
    @Override
    public void unregister(Observer deleteObserver) {

        int observerIndex = locationObserversList.indexOf(deleteObserver);

        locationObserversList.remove(observerIndex);
    }

    /**
     * This method updates the all the observer's state
     * based on changes in the individual's state.
     */
    @Override
    public void notifyObserver() {
        for (Observer observer : locationObserversList) {
            observer.update(this);
        }
    }
}