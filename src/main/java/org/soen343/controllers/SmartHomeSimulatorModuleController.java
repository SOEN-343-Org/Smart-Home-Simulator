package org.soen343.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.services.modules.SHSModule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SmartHomeSimulatorModuleController extends Controller {

    @FXML
    private Pane smartHomeSimulationModule;
    @FXML
    private TextField time;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField outsideTemp;
    @FXML
    private ChoiceBox<String> locationChoices;
    @FXML
    private ChoiceBox<Individual> nameChoices;
    @FXML
    private TextField idToRemove;
    private ObservableList<String> roles = FXCollections.observableArrayList(
            "Family Adult",
            "Family Child",
            "Guest",
            "Stranger");
    @FXML
    private TextField addedName;
    @FXML
    private ChoiceBox<String> roleChoicesAdd;
    @FXML
    private ChoiceBox<String> locationChoicesAdd;
    @FXML
    private TableColumn column1;
    @FXML
    private TableColumn column2;
    @FXML
    private TableColumn column3;
    @FXML
    private TableColumn column4;
    @FXML
    private TableView<Individual> individualsTable;

    private int idSelected;

    private SHSModule shsModule;

    /**
     * Parse s into integer
     *
     * @param s
     * @return false
     */
    private boolean isInteger(String s) {
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
     * Initialize smart home simulation module
     */
    public void initializeController() {

        shsModule = SHSModule.getInstance();

        Logger.getLogger("Testing");

        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("role"));
        column4.setCellValueFactory(new PropertyValueFactory<>("location"));
        column2.setCellFactory(TextFieldTableCell.<Individual>forTableColumn());

        individualsTable.setEditable(true);
        individualsTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                onSelectRow();
            }
        });
        update();
    }

    /**
     * Update location, role, location, name, individuals table
     */
    public void update() {

        if (Model.getSimulationParameters().isSimulationRunning()) {
            disableButtons();
        } else {
            enableButtons();
        }
        ArrayList<String> roomsName = Model.getHouse().roomsName;

        locationChoicesAdd.setItems(FXCollections.observableArrayList(roomsName));
        locationChoicesAdd.setValue("outside");
        roleChoicesAdd.setItems(roles);
        roleChoicesAdd.setValue("Family Adult");

        Individual currentUserIndividual = User.getCurrentIndividual();
        locationChoices.setItems(FXCollections.observableArrayList(roomsName));
        ArrayList<Individual> individualsList = Model.getHouse().getIndividuals();
        individualsTable.setItems(FXCollections.observableArrayList(individualsList));
        nameChoices.setItems(FXCollections.observableArrayList(individualsList));
        individualsTable.refresh();

        if (currentUserIndividual == null) {
            locationChoices.setValue("outside");
            nameChoices.setValue(null);
        } else {
            String currentUserLocation = currentUserIndividual.getLocation();
            locationChoices.setValue(currentUserLocation);
            nameChoices.setValue(currentUserIndividual);
        }
    }

    /**
     * Check selected item and get selected item
     */
    private void onSelectRow() {
        // check the table's selected item and get selected item
        if (individualsTable.getSelectionModel().getSelectedItem() != null) {
            Individual selectedIndividual = individualsTable.getSelectionModel().getSelectedItem();
            idSelected = selectedIndividual.getId();
        }
    }


    @FXML
    private void onEditName(TableColumn.CellEditEvent cellEditEvent) {
        String newName = cellEditEvent.getNewValue().toString();
        if (!newName.isBlank()) {
            boolean success = shsModule.updateIndividualName(newName, idSelected);
            // We could notify the user that the name has been successfully updated
        } else {
            //TODO: inform user name cannot be empty
        }
    }

    @FXML
    private void onAddIndividual(ActionEvent actionEvent) {
        if (!addedName.getText().isBlank()) {
            String name = addedName.getText();
            String role = roleChoicesAdd.getSelectionModel().getSelectedItem();
            String location = locationChoicesAdd.getSelectionModel().getSelectedItem();
            boolean success = shsModule.addNewIndividual(name, role, location);
            // We could notify the user that the individual has been successfully added
        } else {
            //TODO: inform user name cannot be empty
        }
    }

    @FXML
    private void onRemoveIndividual(ActionEvent actionEvent) {
        if (isInteger(idToRemove.getText())) {
            int individualId = Integer.parseInt(idToRemove.getText());
            boolean success = shsModule.removeIndividual(individualId);
            // We could notify the user that the individual has been successfully removed or could not be deleted
        } else {
            //TODO: inform id has to be a number
        }
    }

    @FXML
    private void onCurrentIndividualUpdate(ActionEvent actionEvent) {
        String location = locationChoices.getSelectionModel().getSelectedItem();
        Individual individual = nameChoices.getSelectionModel().getSelectedItem();
        shsModule.updateUserIndividual(individual);
        shsModule.updateIndividualLocation(individual, location);
    }


    @FXML
    private void updateDateTime(ActionEvent actionEvent) {

        if (datePicker.getValue() != null) {
            LocalDate date = datePicker.getValue();
            shsModule.updateDateTimeDate(date);
        }
        if (!time.getText().isBlank()) {
            String t = time.getText();
            String[] timeArray = t.split(":");
            if (timeArray.length == 3 && timeArray[0].length() == 2 && isInteger(timeArray[0]) && timeArray[1].length() == 2 && isInteger(timeArray[1]) && timeArray[2].length() == 2 && isInteger(timeArray[2])) {
                int h = Integer.parseInt(timeArray[0]);
                int m = Integer.parseInt(timeArray[1]);
                int s = Integer.parseInt(timeArray[2]);

                if (h >= 0 && h < 24 && m >= 0 && m < 60 && s >= 0 && s < 60) {
                    shsModule.updateTime(h, m, s);
                }
            }
        }
    }

    @FXML
    private void updateOutsideTemp(ActionEvent ae) {
        String t = outsideTemp.getText();
        if (isInteger(t)) {
            int temp = Integer.parseInt(t);
            shsModule.updateOutsideTemp(temp);
        }
    }

    public void disableButtons(){
        smartHomeSimulationModule.setDisable(true);
    }

    public void enableButtons(){
        smartHomeSimulationModule.setDisable(false);
    }

}
