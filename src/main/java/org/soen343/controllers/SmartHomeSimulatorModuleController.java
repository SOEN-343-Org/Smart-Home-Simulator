package org.soen343.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import org.soen343.models.Individual;
import org.soen343.services.SmartHomeSimulatorModuleService;

import java.time.LocalDate;
import java.util.ArrayList;

public class SmartHomeSimulatorModuleController extends Controller {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hours;
    @FXML
    private TextField minutes;
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

    private SmartHomeSimulatorModuleService smartHomeSimulatorModuleService;

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

    @FXML
    public void initialize() {
    }

    public void initializeController() {

        smartHomeSimulatorModuleService = new SmartHomeSimulatorModuleService();

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
        setTableAndComboChoice();

        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            updateDate(newValue);
        });
    }

    private void setTableAndComboChoice() {
        ArrayList<String> roomsName = smartHomeSimulatorModuleService.getHouseRoomsName();

        locationChoicesAdd.setItems(FXCollections.observableArrayList(roomsName));
        locationChoicesAdd.setValue("outside");
        roleChoicesAdd.setItems(roles);
        roleChoicesAdd.setValue("Family Adult");

        String currentUserLocation = smartHomeSimulatorModuleService.getCurrentUserLocation();
        locationChoices.setItems(FXCollections.observableArrayList(roomsName));
        locationChoices.setValue(currentUserLocation);

        ArrayList<Individual> individualsList = smartHomeSimulatorModuleService.getIndividuals();
        Individual currentUserIndividual = smartHomeSimulatorModuleService.getCurrentUserIndividual();

        nameChoices.setItems(FXCollections.observableArrayList(individualsList));
        nameChoices.setValue(currentUserIndividual);

        individualsTable.setItems(FXCollections.observableArrayList(individualsList));
        individualsTable.refresh();

    }


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
            boolean success = smartHomeSimulatorModuleService.updateIndividualName(newName, idSelected);
            // We could notify the user that the name has been successfully updated
        } else {
            //TODO: inform user name cannot be empty
        }
        setTableAndComboChoice();
        mainController.update();
    }

    public void onAddIndividual(ActionEvent actionEvent) {
        if (!addedName.getText().isBlank()) {
            String name = addedName.getText();
            String role = roleChoicesAdd.getSelectionModel().getSelectedItem();
            String location = locationChoicesAdd.getSelectionModel().getSelectedItem();
            boolean success = smartHomeSimulatorModuleService.addNewIndividual(name, role, location);
            // We could notify the user that the individual has been successfully added
        } else {
            //TODO: inform user name cannot be empty
        }
        setTableAndComboChoice();
        mainController.update();
    }

    public void onRemoveIndividual(ActionEvent actionEvent) {
        if (isInteger(idToRemove.getText())) {
            int individualId = Integer.parseInt(idToRemove.getText());
            boolean success = smartHomeSimulatorModuleService.RemoveIndividual(individualId);
            // We could notify the user that the individual has been successfully removed or could not be deleted
        } else {
            //TODO: inform id has to be a number
        }
        setTableAndComboChoice();
        mainController.update();
    }

    public void onCurrentIndividualUpdate(ActionEvent actionEvent) {
        String location = locationChoices.getSelectionModel().getSelectedItem();
        Individual individual = nameChoices.getSelectionModel().getSelectedItem();
        smartHomeSimulatorModuleService.updateIndividualLocation(individual, location);
        setTableAndComboChoice();
        mainController.update();
    }

    @FXML
    private void updateTimeHours(ActionEvent ae) {
        String h = hours.getText();
        if (isInteger(h)) {
            int hours = Integer.parseInt(h);
            smartHomeSimulatorModuleService.updateDateTimeHours(hours);
        }
        mainController.update();
        mainController.updateContextInfo();
    }

    @FXML
    private void updateTimeMinutes(ActionEvent ae) {
        String m = minutes.getText();
        if (isInteger(m)) {
            int minutes = Integer.parseInt(m);
            smartHomeSimulatorModuleService.updateDateTimeMinutes(minutes);
        }
        mainController.update();
        mainController.updateContextInfo();
    }

    public void updateDate(LocalDate d) {
        smartHomeSimulatorModuleService.updateDateTimeDate(d);
        mainController.update();
        mainController.updateContextInfo();
    }

}
