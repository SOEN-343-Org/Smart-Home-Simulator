package org.soen343.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import org.soen343.models.Individual;
import org.soen343.models.Model;

import java.io.IOException;
import java.sql.Connection;

/**
 * The type Individual controller.
 */
public class IndividualController extends Controller {

    // idSelected is used to track which individual is selected for editing.
    private static Integer idSelected;
    ObservableList<Individual> individualsList;
    @FXML
    private TableView<Individual> individualsTable;
    @FXML
    private TableColumn<Individual, String> col_name;
    @FXML
    private TableColumn<Individual, String> col_role;
    @FXML
    private TableColumn<Individual, String> col_location;
    @FXML
    private TableColumn<Individual, Integer> col_id;
    @FXML
    private TextField addedName;
    @FXML
    private ChoiceBox<String> roleChoices;
    @FXML
    private ChoiceBox<String> locationChoices;
    @FXML
    private TextField idToRemove;
    // dummy username of logged in username TODO : figure out how to get logged in user's username
    private String username = "test123";
    // Roles to select from
    private ObservableList<String> roles = FXCollections.observableArrayList(
            "Family Adult",
            "Family Child",
            "Guest",
            "Stranger");

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
        ind.location = newLocation;
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
        Model.updateIndividualRole(newRole, idSelected);
        ind.role = newRole;
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
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {

        // set up the columns with titles in the individuals table
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("roleChoices"));
        col_location.setCellValueFactory(new PropertyValueFactory<>("locationChoices"));


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

    }

    public void init() {
        // This fetches the individuals from the model
        model.setIndividualsFromUser(username);
        individualsList = FXCollections.observableArrayList(Model.house.individuals.values());
        individualsTable.setItems(individualsList);

        // Load options for Locations Choicebox with outside as default
        locationChoices.setItems(FXCollections.observableArrayList(Model.house.roomsName));
        locationChoices.setValue("outside");

    }


    /**
     * This method allows a user to double click a cell to edit
     * and update the Name column in the table and database.
     *
     * @param editedCell TableColumn.CellEditEvent The edited cell
     */
    @FXML
    public void onEditName(TableColumn.CellEditEvent editedCell) {
        String newName = editedCell.getNewValue().toString();
        model.updateIndividualName(newName, idSelected);
        individualsList = FXCollections.observableArrayList(Model.house.individuals.values());
        individualsTable.setItems(individualsList);
    }

    /**
     * This method brings the user back to the
     * Edit Context of Simulation page.
     *
     * @throws IOException
     */
    @FXML
    private void cancelPushed() throws IOException {
//        App.setRoot("HouseLayoutView");
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
        if (!addedName.getText().trim().isEmpty()) {
            model.addIndividual(addedName.getText(), roleChoices.getSelectionModel().getSelectedItem(), username, locationChoices.getSelectionModel().getSelectedItem());
        }
        individualsList = FXCollections.observableArrayList(Model.house.individuals.values());
        individualsTable.setItems(individualsList);
    }

    /**
     * This method removes an existing individual
     * in the database and the view.
     */
    @FXML
    private void onRemoveIndividual() {
        if (isInteger(idToRemove.getText())) {
            model.removeIndividual(Integer.parseInt(idToRemove.getText()));
            individualsList = FXCollections.observableArrayList(Model.house.individuals.values());
            individualsTable.setItems(individualsList);
        }
    }
}
