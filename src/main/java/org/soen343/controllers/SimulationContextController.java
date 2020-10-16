package org.soen343.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.soen343.models.Individual;
import org.soen343.models.Model;
import org.soen343.models.Room;
import org.soen343.models.Window;

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
    private ChoiceBox<String> nameChoices;
    @FXML
    private ChoiceBox<String> locationChoices;

    private Set<TreeItem<String>> blockUnblockSelected;

    public void init() {

        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("role"));
        column4.setCellValueFactory(new PropertyValueFactory<>("location"));
        column2.setCellFactory(TextFieldTableCell.<Individual>forTableColumn());

        setIndividualsTable();
        setComboBox();
        // BLOCK AND UNBLOCK of WINDOWS

        CheckBoxTreeItem<String> root2 = new CheckBoxTreeItem<>("Rooms");
        root2.setExpanded(true);
        for (Room room : Model.house.getRooms()) {

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

        blockUnblockTreeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
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
    }

    public void updateInfo() {
        setIndividualsTable();
        setComboBox();
    }

    private void setComboBox() {
        ArrayList<String> roomsName = Model.house.roomsName;
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

        for (int windowId : windowsToUpdate) {
            Window window = Model.house.getWindowById(windowId);
            window.setBlocked(!window.isBlocked());
            // TODO: Need to log that we block/unblock a window #windowId
        }
        mainController.drawLayout();
    }

    public void exitSimulationContext(ActionEvent actionEvent) {
        mainController.exitSimulationContext();
    }

    @FXML
    private void updateIndividualLocation(ActionEvent actionEvent) {
        String location = locationChoices.getSelectionModel().getSelectedItem();
        String name = nameChoices.getSelectionModel().getSelectedItem();
        Individual ind = Model.house.getIndividualByName(name);
        ind.setLocation(location);
        setIndividualsTable();
        setComboBox();
        mainController.drawLayout();
    }
}
