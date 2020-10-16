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
import org.soen343.models.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class SmartHomeContextController extends Controller {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hours;
    @FXML
    private TextField minutes;
    @FXML
    private ChoiceBox<String> locationChoices;
    @FXML
    private ChoiceBox<String> nameChoices;
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

    @FXML
    private void initialize() {

    }

    public void init() {
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("role"));
        column4.setCellValueFactory(new PropertyValueFactory<>("location"));
        column2.setCellFactory(TextFieldTableCell.<Individual>forTableColumn());

        setIndividualsTable();
        individualsTable.setEditable(true);
        individualsTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                onSelectRow();
            }
        });
        setComboBox();
    }

    private void onSelectRow() {
        // check the table's selected item and get selected item
        if (individualsTable.getSelectionModel().getSelectedItem() != null) {
            Individual selectedIndividual = individualsTable.getSelectionModel().getSelectedItem();
            idSelected = selectedIndividual.getId();
        }
    }

    private void setComboBox() {
        ArrayList<String> roomsName = Model.house.roomsName;
        locationChoicesAdd.setItems(FXCollections.observableArrayList(roomsName));
        locationChoicesAdd.setValue("outside");
        roleChoicesAdd.setItems(roles);
        roleChoicesAdd.setValue("Family Adult");

        locationChoices.setItems(FXCollections.observableArrayList(roomsName));
        locationChoices.setValue(model.user.getCurrentIndividual().getLocation());
        ArrayList<String> names = new ArrayList<>();
        for (Individual ind : Model.house.individuals.values()) {
            names.add(ind.getName());
        }

        nameChoices.setItems(FXCollections.observableArrayList(names));
        nameChoices.setValue(model.user.getCurrentIndividual().getName());
    }


    private void setIndividualsTable() {
        individualsTable.setItems(FXCollections.observableArrayList(Model.house.individuals.values()));
        individualsTable.refresh();
    }

    @FXML
    private void onEditName(TableColumn.CellEditEvent cellEditEvent) {
        String newName = cellEditEvent.getNewValue().toString();
        model.updateIndividualName(newName, idSelected);
        setIndividualsTable();
        setComboBox();
    }

    public void onAddIndividual(ActionEvent actionEvent) {
        if (!addedName.getText().trim().isEmpty()) {
            model.addIndividual(addedName.getText(), roleChoicesAdd.getSelectionModel().getSelectedItem(), model.user.getName(), locationChoicesAdd.getSelectionModel().getSelectedItem());
            setIndividualsTable();
            setComboBox();
            mainController.drawLayout();
        }
    }

    public void onRemoveIndividual(ActionEvent actionEvent) {
        if (isInteger(idToRemove.getText())) {
            model.removeIndividual(Integer.parseInt(idToRemove.getText()));
            setIndividualsTable();
            setComboBox();
            mainController.drawLayout();
        }
    }

    public void onCurrentIndividualUpdate(ActionEvent actionEvent) {
        String location = locationChoices.getSelectionModel().getSelectedItem();
        String name = nameChoices.getSelectionModel().getSelectedItem();
        Individual ind = Model.house.getIndividualByName(name);
        model.user.setCurrentIndividual(ind);
        model.user.getCurrentIndividual().setLocation(location);
        setIndividualsTable();
        setComboBox();
        mainController.drawLayout();
    }

    @FXML
    private void updateTimeHours(MouseEvent mouseEvent) {
        String h = hours.getText();
        if (isInteger(h)) {
            model.time.setHours(Integer.parseInt(h));
        }
    }

    @FXML
    private void updateTimeMinutes(MouseEvent mouseEvent) {
        String m = minutes.getText();
        if (isInteger(m)) {
            model.time.setMinutes(Integer.parseInt(m));
        }
    }

    public void updateDate(MouseEvent mouseEvent) {
        LocalDate d = datePicker.getValue();
        model.time.setDate(d);
    }
}
