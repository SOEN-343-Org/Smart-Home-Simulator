package org.soen343.controller;

import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import org.soen343.connection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import org.soen343.App;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.soen343.models.*;

/**
 * The type Individual controller.
 */
public class IndividualController {

    @FXML private TableView <Individual> individualsTable;
    @FXML private TableColumn <Individual, String> col_name;
    @FXML private TableColumn <Individual, String> col_role;
    @FXML private TableColumn <Individual, String> col_location;
    @FXML private TableColumn <Individual, Integer> col_id;
    @FXML private TextField addedName;
    @FXML private ChoiceBox roleChoices;
    @FXML private ChoiceBox locationChoices;
    @FXML private TextField idToRemove;
    private static Connection dbCon;
    // TODO : change db source
    private static String dbTable = "individuals";
    // dummy username of logged in username TODO : figure out how to get logged in user's username
    private String username = "flemmingway";

    // idSelected is used to track which individual is selected for editing.
    private static Integer idSelected;

    // Roles to select from
    private ObservableList roles = FXCollections.observableArrayList(
            "Family Adult",
            "Family Child",
            "Guest",
            "Stranger");
    private ComboBox individualRoleChoices = new ComboBox(roles);


    // locations to select from ( TODO : to be provided by house layout specs )
    private ObservableList locations = FXCollections.observableArrayList(
            "Room 1",
            "Room 2",
            "Room 3",
            "Outside");
    private ComboBox individualLocationChoices = new ComboBox(locations);

    /**
     * Initialize.
     */
    @FXML
    public void initialize()  {

        // set up the columns with titles in the individuals table
        col_id.setCellValueFactory(new PropertyValueFactory<Individual, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Individual, String>("name"));
        col_role.setCellValueFactory(new PropertyValueFactory<Individual, String>("roleChoices"));
        col_location.setCellValueFactory(new PropertyValueFactory<Individual, String>("locationChoices"));

        individualsTable.setItems(getExistingIndividuals());

        // When a row is clicked once, a function that records its individual ID is triggered.
        individualsTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                onSelectRow();
            }
        });

        // edit the table to allow Name text field to be editable
        individualsTable.setEditable(true);

        // on double click, makes selected cell an editable text field
        col_name.setCellFactory(TextFieldTableCell.<Individual>forTableColumn());

        // Load options for Roles Choicebox with Family Adult as default
        roleChoices.setItems(roles);
        roleChoices.setValue("Family Adult");

        // Load options for Locations Choicebox with Outside as default
        locationChoices.setItems(locations);
        locationChoices.setValue("Outside");
    }

    /**
     * This method fetches the existing individuals from the database.
     * @return individualsList ObservableList,<Individual>
     *     is the list of existing individuals in the database.
     * TODO : only display individuals that have the attribute username='desired username'
     */
    private ObservableList<Individual> getExistingIndividuals() {
        // the list of individuals that will go in the table
        ObservableList<Individual> individualsList = FXCollections.observableArrayList();

        try {
            dbCon = DBConnection.getConnection();

            // existing individuals set
            ResultSet rs = dbCon.createStatement().executeQuery("select * from individuals");

            while (rs.next()){
                Individual p = new Individual(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("location"),
                        username);
                p.setRoleChoices(roles);
                p.setLocationChoices(locations);
                individualsList.add(p);
            }

        } catch (SQLException e) {
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
        }

        return individualsList;
    }

    /**
     * This method allows a user to double click a cell to edit
     * and update the Name column in the table and database.
     *
     * @param editedCell TableColumn.CellEditEvent The edited cell
     */
    @FXML
    public void onEditName(TableColumn.CellEditEvent editedCell) {
        Individual individualSelected = individualsTable.getSelectionModel().getSelectedItem();
        String newName = editedCell.getNewValue().toString();
        individualSelected.setName(newName);

        try {
            dbCon.createStatement().executeUpdate(
                    "update "+ dbTable +
                    " set name = '"+ newName +
                    "' where id = " + idSelected);
        } catch (SQLException e) {
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This method allows a user to double click a cell to edit
     * and update the Location column in the table and the database.
     *
     * @param location Object location of the individual
     * @param id       Integer id of the individual
     */
    @FXML
    public static void onEditLocation(Object location, Integer id) {
        String newLocation = String.valueOf(location);
        idSelected = id;

        try {
            dbCon.createStatement().executeUpdate("UPDATE "+ dbTable +
                    " SET location = '"+ newLocation +
                    "' WHERE id = " + idSelected);
        } catch (SQLException e) {
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * This method updates the individual's Role in the database.
     *
     * @param role Object role
     * @param id   Integer id
     */
    public static void onEditRole(Object role, Integer id) {
        String newRole = String.valueOf(role);
        idSelected = id;

        try {
            dbCon.createStatement().executeUpdate(
                    "UPDATE "+ dbTable +
                    " SET role = '"+ newRole +
                    "' WHERE id = " + idSelected);
        } catch (SQLException e) {
            Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This method brings the user back to the
     * Edit Context of Simulation page.
     *  TODO : figure out how to switch pages if setRoot is private
     * @throws IOException
     */
    @FXML
    private void cancelPushed() throws IOException {
//        App.setRoot("editContext");
    }

    /**
     * This method gets the ID of the individual in the
     * table row.
     */
    @FXML
    private void onSelectRow() {
        // check the table's selected item and get selected item
        if (individualsTable.getSelectionModel().getSelectedItem() != null) {
            Individual selectedIndividual = individualsTable.getSelectionModel().getSelectedItem();
            idSelected = selectedIndividual.getId();
        }
    }

    /**
     * This method adds a new individual
     * in the database and the view.
     */
    @FXML
    private void onAddIndividual() {

        if (!addedName.getText().trim().isEmpty()){
            try {
                dbCon.createStatement().executeUpdate("INSERT into "+ dbTable +
                        " (name,role,location,username) VALUES ('"+ addedName.getText() + "','" +
                        roleChoices.getSelectionModel().getSelectedItem() + "','" +
                        locationChoices.getSelectionModel().getSelectedItem() + "','" +
                        username + "')");
            } catch (SQLException e) {
                Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        individualsTable.setItems(getExistingIndividuals());
    }

    /**
     * This is a utility method that verifies whether a String is an Integer.
     * It returns true if it is
     * else false.
     *
     * @param s the s
     * @return boolean boolean
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * This method removes an existing individual
     * in the database and the view.
     *
     */
    @FXML
    private void onRemoveIndividual() {

        if (isInteger(idToRemove.getText())) {
            try {
                dbCon.createStatement().executeUpdate("DELETE from "+ dbTable +
                        " where id="+idToRemove.getText());
            } catch (SQLException e) {
                Logger.getLogger(IndividualController.class.getName()).log(Level.SEVERE, null, e);
            }
            individualsTable.setItems(getExistingIndividuals());
        }
    }
}
