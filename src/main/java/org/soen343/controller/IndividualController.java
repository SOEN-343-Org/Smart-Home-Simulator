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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.soen343.models.*;
import org.soen343.util.HouseLayoutUtil;

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

    // TODO : change db source if not local
    private static String dbTableName = "individuals";
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

    // locations to select from
    private ObservableList locations = HouseLayoutUtil.roomNames;
    private ComboBox individualLocationChoices = new ComboBox(locations);

    // locations information from House layout specs
    private House h;
    private ArrayList<Room> locationsList;

    ObservableList<Individual> individualsList;

    /**
     * Initialize.
     */
    @FXML
    public void initialize()  {

        // TODO: to remove, use this bc no redirect button to manage individuals yet
        h = HouseLayoutUtil.ReadHouseLayoutFile();
        // list of room objects from house layout specs
        locationsList = HouseLayoutUtil.roomList;

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

        // Load options for Locations Choicebox with kitchen as default
        locationChoices.setItems(locations);
        locationChoices.setValue("kitchen");
    }

    /**
     * This method fetches the existing individuals from the database.
     * @return individualsList ObservableList,<Individual>
     *     is the list of existing individuals in the database.
     */
    private ObservableList<Individual> getExistingIndividuals() {
        // the list of individuals that will go in the table
        individualsList = FXCollections.observableArrayList();

        try {
            dbCon = DBConnection.getConnection();

            ResultSet rs = Individual.getIndividualsDB(username, dbCon);

            while (rs.next()){
                Individual p = new Individual(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("location"),
                        username);
                p.setRoleChoices(roles);
                p.setLocationChoices(locations);
                p.setLocationObserversList(locationsList);
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
        Individual.updateNameDB(dbCon, dbTableName, newName, idSelected);
    }

    /**
     * This method allows a user to double click a cell to edit
     * and update the Location column in the table and the database.
     *
     * @param location Object location of the individual
     * @param id       Integer id of the individual
     */
    @FXML
    public static void onEditLocation(Object location, Integer id, Individual ind) {
        String newLocation = String.valueOf(location);
        idSelected = id;
        Individual.updateLocationDB(dbCon, dbTableName, newLocation, idSelected);
        ind.location = newLocation;
        ind.notifyObserver();
    }

    /**
     * This method updates the individual's Role in the database.
     *
     * @param role Object role
     * @param id   Integer id
     */
    public static void onEditRole(Object role, Integer id, Individual ind) {
        String newRole = String.valueOf(role);
        idSelected = id;
        Individual.updateRoleDB(dbCon, dbTableName, newRole, idSelected);
        ind.role = newRole;
        ind.notifyObserver();
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
           Individual.addIndividualDB(dbCon, dbTableName, addedName, roleChoices, locationChoices, username);
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
          Individual.removeIndividualDB(dbCon, dbTableName, idToRemove);
            removeFromReporterList();
            individualsTable.setItems(getExistingIndividuals());
        }
    }

    /**
     * I will use reporter interchangeably with individual.
     * This method removes a reporter from
     * the list of existing reporters, updates its location
     * to empty since it does not exist anywhere
     * and notifies all observers that this individual is gone.
     */
    private void removeFromReporterList() {
        individualsList.forEach((ind) -> {
            if (ind.getId() == Integer.parseInt(idToRemove.getText())) {
                ind.location="";
                ind.notifyObserver();
            }
        });
    }
}
