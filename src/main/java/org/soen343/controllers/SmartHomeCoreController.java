package org.soen343.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import org.soen343.models.*;

import java.util.HashSet;
import java.util.Set;

public class SmartHomeCoreController extends Controller {
    @FXML
    public TreeView<String> openCloseTreeView;
    @FXML
    public TreeView<String> blockUnblockTreeView;

    private Set<TreeItem<String>> openCloseSelected;
    private Set<TreeItem<String>> blockUnblockSelected;

    private DashboardController mainController;

    public void setMainController(DashboardController c) {
        mainController = c;
    }

    @FXML
    public void initialize() {

    }

    public void init() {

        // OPEN AND CLOSE of DOORS LIGHTS AND WINDOWS
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Rooms");
        root.setExpanded(true);
        for (Room room : Model.house.getRooms()) {
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


    @FXML
    private void openClose(ActionEvent actionEvent) {

        if (!model.simulationStarted) {
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
            Door door = Model.house.getDoorById(doorId);
            door.setOpen(!door.isOpen());
            // TODO: Need to log that we open a door #doorId
        }
        for (int windowId : windowsToUpdate) {
            Window window = Model.house.getWindowById(windowId);
            window.setOpen(!window.isOpen());
            // TODO: Need to log that we open a window #windowId
        }
        for (int lightId : lightsToUpdate) {
            Light light = Model.house.getLightById(lightId);
            light.setOpen(!light.isOpen());
            // TODO: Need to log that we open a light #lightId
        }
        mainController.drawLayout();
    }


    @FXML
    private void blockUnblock(ActionEvent actionEvent) {

        if (!model.simulationStarted) {
            // Simulation is not on
            return;
        }
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
}
