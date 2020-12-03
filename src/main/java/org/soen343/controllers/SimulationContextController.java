package org.soen343.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.soen343.models.Model;
import org.soen343.models.house.Individual;
import org.soen343.models.house.Room;
import org.soen343.models.house.Window;
import org.soen343.services.SimulationContextService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SimulationContextController extends Controller {

    @FXML
    private TableColumn column1;
    @FXML
    private TableColumn column2;
    @FXML
    private TableColumn column3;
    @FXML
    private TableColumn column4;
    @FXML
    private TreeView<String> blockUnblockTreeView;
    @FXML
    private TableView<Individual> individualsTable;
    @FXML
    private ChoiceBox<Individual> nameChoices;
    @FXML
    private ChoiceBox<String> locationChoices;

    private Set<TreeItem<String>> blockUnblockSelected;

    private SimulationContextService simulationContextService;

    @FXML
    public void initialize() {
    }

    /**
     * Initialize context controller
     */
    public void initializeController() {

        simulationContextService = SimulationContextService.getInstance();

        setColumnValues();

        // BLOCK AND UNBLOCK of WINDOWS

        CheckBoxTreeItem<String> root2 = new CheckBoxTreeItem<>("Rooms");
        root2.setExpanded(true);

        ArrayList<Room> rooms = Model.getHouse().getRooms();

        for (Room room : rooms) {

            if (room.getWindows().size() > 0) {
                CheckBoxTreeItem<String> superItem = new CheckBoxTreeItem<>(room.getName());
                CheckBoxTreeItem<String> windowsItem = new CheckBoxTreeItem<>("Windows");
                for (Window window : room.getWindows()) {
                    CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(window.getName());
                    windowsItem.getChildren().add(item);
                }
                superItem.getChildren().add(windowsItem);
                root2.getChildren().add(superItem);
            }
        }
        blockUnblockTreeView.setRoot(root2);

        blockUnblockTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        blockUnblockSelected = new HashSet<>();
        root2.addEventHandler(CheckBoxTreeItem.checkBoxSelectionChangedEvent(), (CheckBoxTreeItem.TreeModificationEvent<String> evt) -> {
            CheckBoxTreeItem<String> item = evt.getTreeItem();

            if (evt.wasIndeterminateChanged()) {
                if (item.isIndeterminate()) {
                    blockUnblockSelected.remove(item);
                } else if (item.isSelected()) {
                    blockUnblockSelected.add(item);
                }
            } else if (evt.wasSelectionChanged()) {
                if (item.isSelected()) {
                    blockUnblockSelected.add(item);
                } else {
                    blockUnblockSelected.remove(item);
                }
            }
        });
        update();
    }

    /**
     * Refactored intializeController Function
     * Extracted method to set the values for the column
     */
    public void setColumnValues(){
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("role"));
        column4.setCellValueFactory(new PropertyValueFactory<>("location"));
        column2.setCellFactory(TextFieldTableCell.<Individual>forTableColumn());
    }

    @Override
    void update() {
        ArrayList<String> roomsName = Model.getHouse().roomsName;
        locationChoices.setItems(FXCollections.observableArrayList(roomsName));
        locationChoices.setValue("outside");
        ArrayList<Individual> individualsList = Model.getHouse().getIndividuals();
        nameChoices.setItems(FXCollections.observableArrayList(individualsList));
        nameChoices.setValue(null);
        individualsTable.setItems(FXCollections.observableArrayList(individualsList));
        individualsTable.refresh();
    }

    @FXML
    private void blockUnblock(ActionEvent actionEvent) {
        HashSet<Integer> windowsToUpdate = new HashSet<>();

        for (TreeItem<String> item : blockUnblockSelected) {
            if (item.isLeaf()) {
                String value = item.getValue();
                String type = value.split(" ")[0];
                if ("Window".equals(type)) {
                    windowsToUpdate.add(Integer.parseInt(value.split(" ")[1].replace("#", "")));
                }
            }
        }
        simulationContextService.updateWindowBlockState(windowsToUpdate);

    }

    @FXML
    private void updateIndividualLocation(ActionEvent actionEvent) {
        String location = locationChoices.getSelectionModel().getSelectedItem();
        Individual individual = nameChoices.getSelectionModel().getSelectedItem();
        simulationContextService.updateIndividualLocation(individual, location);
    }

    /**
     * Exit simulation
     *
     * @param actionEvent
     */
    public void exitSimulationContext(ActionEvent actionEvent) {
        mainController.exitSimulationContext();
    }

}
