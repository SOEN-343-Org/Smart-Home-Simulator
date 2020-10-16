package org.soen343.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import org.soen343.models.Door;
import org.soen343.models.Light;
import org.soen343.models.Room;
import org.soen343.models.Window;
import org.soen343.services.SmartHomeCoreModuleService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SmartHomeCoreModuleController extends Controller {
    @FXML
    public TreeView<String> openCloseTreeView;
    SmartHomeCoreModuleService smartHomeCoreModuleService;
    private Set<TreeItem<String>> openCloseSelected;

    @FXML
    public void initialize() {
    }

    public void initializeController() {

        smartHomeCoreModuleService = new SmartHomeCoreModuleService();

        // OPEN AND CLOSE of DOORS LIGHTS AND WINDOWS
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Rooms");
        root.setExpanded(true);

        ArrayList<Room> rooms = smartHomeCoreModuleService.getHouseRooms();

        for (Room room : rooms) {
            CheckBoxTreeItem<String> superItem = new CheckBoxTreeItem<>(room.getName());
            if (room.getWindows().size() > 0) {
                CheckBoxTreeItem<String> windowsItem = new CheckBoxTreeItem<>("Windows");
                for (Window window : room.getWindows()) {
                    CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(window.getName());
                    windowsItem.getChildren().add(item);
                }
                superItem.getChildren().add(windowsItem);
            }

            if (room.getDoors().size() > 0) {
                CheckBoxTreeItem<String> doorsItem = new CheckBoxTreeItem<>("Doors");
                for (Door door : room.getDoors()) {
                    CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(door.getName());
                    doorsItem.getChildren().add(item);
                }
                superItem.getChildren().add(doorsItem);
            }

            if (room.getDoors().size() > 0) {
                CheckBoxTreeItem<String> lightsItem = new CheckBoxTreeItem<>("Lights");
                for (Light light : room.getLights()) {
                    CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(light.getName());
                    lightsItem.getChildren().add(item);
                }
                superItem.getChildren().add(lightsItem);
            }


            root.getChildren().add(superItem);
        }
        openCloseTreeView.setRoot(root);

        openCloseTreeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        openCloseSelected = new HashSet<>();
        root.addEventHandler(CheckBoxTreeItem.checkBoxSelectionChangedEvent(), (CheckBoxTreeItem.TreeModificationEvent<String> evt) -> {
            CheckBoxTreeItem<String> item = evt.getTreeItem();

            if (evt.wasIndeterminateChanged()) {
                if (item.isIndeterminate()) {
                    openCloseSelected.remove(item);
                } else if (item.isSelected()) {
                    openCloseSelected.add(item);
                }
            } else if (evt.wasSelectionChanged()) {
                if (item.isSelected()) {
                    openCloseSelected.add(item);
                } else {
                    openCloseSelected.remove(item);
                }
            }
        });
    }


    @FXML
    private void openClose(ActionEvent actionEvent) {

        if (!smartHomeCoreModuleService.SimulationIsRunning()) {
            // Simulation is not on
            return;
        }
        HashSet<Integer> doorsToUpdate = new HashSet<>();
        HashSet<Integer> windowsToUpdate = new HashSet<>();
        HashSet<Integer> lightsToUpdate = new HashSet<>();

        for (TreeItem<String> item : openCloseSelected) {
            if (item.isLeaf()) {
                String value = item.getValue();
                String type = value.split(" ")[0];
                switch (type) {
                    case "Door":
                        doorsToUpdate.add(Integer.parseInt(value.split(" ")[1].replace("#", "")));
                        break;
                    case "Window":
                        windowsToUpdate.add(Integer.parseInt(value.split(" ")[1].replace("#", "")));
                        break;
                    case "Light":
                        lightsToUpdate.add(Integer.parseInt(value.split(" ")[1].replace("#", "")));
                        break;
                }
            }
        }

        for (int doorId : doorsToUpdate) {
            smartHomeCoreModuleService.updateDoorState(doorId);
        }
        for (int windowId : windowsToUpdate) {
            smartHomeCoreModuleService.updateWindowState(windowId);
        }
        for (int lightId : lightsToUpdate) {
            smartHomeCoreModuleService.updateLightState(lightId);
        }
        mainController.update();
    }
}
