package org.soen343.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import org.soen343.models.Model;
import org.soen343.models.house.Door;
import org.soen343.models.house.Light;
import org.soen343.models.house.Room;
import org.soen343.models.house.Window;
import org.soen343.services.modules.SHCModule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SmartHomeCoreModuleController extends Controller {
    @FXML
    public TreeView<String> openCloseTreeView;
    private Set<TreeItem<String>> openCloseSelected;

    private SHCModule shcModule;

    @FXML
    public void initialize() {
    }

    /**
     * Initialize smart home core controller module controller
     */
    public void initializeController() {

        shcModule = SHCModule.getInstance();

        // OPEN AND CLOSE of DOORS LIGHTS AND WINDOWS
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Rooms");
        root.setExpanded(true);

        ArrayList<Room> rooms = Model.getHouse().getRooms();

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

    @Override
    void update() {

    }


    @FXML
    private void openClose(ActionEvent actionEvent) {

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

        shcModule.updateDoorState(doorsToUpdate);
        shcModule.updateWindowState(windowsToUpdate);
        shcModule.updateLightState(lightsToUpdate);
    }
}
