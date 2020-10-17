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
        update();
    }

    public void update() {
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
        mainController.update();
    }

    @FXML
    private void onAddIndividual(ActionEvent actionEvent) {
        if (!addedName.getText().isBlank()) {
            String name = addedName.getText();
            String role = roleChoicesAdd.getSelectionModel().getSelectedItem();
            String location = locationChoicesAdd.getSelectionModel().getSelectedItem();
            boolean success = smartHomeSimulatorModuleService.addNewIndividual(name, role, location);
            // We could notify the user that the individual has been successfully added
        } else {
            //TODO: inform user name cannot be empty
        }
        mainController.update();
    }

    @FXML
    private void onRemoveIndividual(ActionEvent actionEvent) {
        if (isInteger(idToRemove.getText())) {
            int individualId = Integer.parseInt(idToRemove.getText());
            boolean success = smartHomeSimulatorModuleService.RemoveIndividual(individualId);
            // We could notify the user that the individual has been successfully removed or could not be deleted
        } else {
            //TODO: inform id has to be a number
        }
        mainController.update();
    }

    @FXML
    private void onCurrentIndividualUpdate(ActionEvent actionEvent) {
        String location = locationChoices.getSelectionModel().getSelectedItem();
        Individual individual = nameChoices.getSelectionModel().getSelectedItem();
        System.out.println(individual);
        smartHomeSimulatorModuleService.updateUserIndividual(individual);
        smartHomeSimulatorModuleService.updateIndividualLocation(individual, location);
        mainController.update();
    }


    @FXML
    private void updateDateTime(ActionEvent actionEvent) {

        if (datePicker.getValue() != null) {
            LocalDate date = datePicker.getValue();
            smartHomeSimulatorModuleService.updateDateTimeDate(date);
        }
        if (!time.getText().isBlank()) {
            String t = time.getText();
            String[] timeArray = t.split(":");
            if (timeArray.length == 3 && timeArray[0].length() == 2 && isInteger(timeArray[0]) && timeArray[1].length() == 2 && isInteger(timeArray[1]) && timeArray[2].length() == 2 && isInteger(timeArray[2])) {
                int h = Integer.parseInt(timeArray[0]);
                int m = Integer.parseInt(timeArray[1]);
                int s = Integer.parseInt(timeArray[2]);

                if (h >= 0 && h < 24 && m >= 0 && m < 60 && s >= 0 && s < 60) {
                    smartHomeSimulatorModuleService.updateTime(h, m, s);
                }
            }
        }
        mainController.update();
    }

    @FXML
    private void updateOutsideTemp(ActionEvent ae) {
        String t = outsideTemp.getText();
        if (isInteger(t)) {
            int temp = Integer.parseInt(t);
            smartHomeSimulatorModuleService.updateOutsideTemp(temp);
        }
        mainController.update();
    }

}
