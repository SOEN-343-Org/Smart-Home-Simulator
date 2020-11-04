package org.soen343.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import org.soen343.models.Model;
import org.soen343.models.house.Components;
import org.soen343.models.house.Room;
import org.soen343.services.modules.SHCModule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SmartHomeCoreModuleController extends Controller {

    @FXML
    private Button doorsButton;
    @FXML
    private Button windowsButton;
    @FXML
    private Button lightsButton;
    @FXML
    private ToggleButton autoModeButton;
    @FXML
    private TreeView<String> doorsTreeView;
    @FXML
    private TreeView<String> windowsTreeView;
    @FXML
    private TreeView<String> lightsTreeView;

    private Set<TreeItem<String>> doorsSelected;
    private Set<TreeItem<String>> windowsSelected;
    private Set<TreeItem<String>> lightsSelected;

    private SHCModule shcModule;


    /**
     * Initialize smart home core controller module controller
     */
    public void initializeController() {

        shcModule = SHCModule.getInstance();
        CheckBoxTreeItem<String> d = initializeTreeView("Doors");
        doorsTreeView.setRoot(d);
        CheckBoxTreeItem<String> w = initializeTreeView("Windows");
        windowsTreeView.setRoot(w);
        CheckBoxTreeItem<String> l = initializeTreeView("Lights");
        lightsTreeView.setRoot(l);

        doorsTreeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        windowsTreeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        lightsTreeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        doorsSelected = new HashSet<>();
        windowsSelected = new HashSet<>();
        lightsSelected = new HashSet<>();
        addListener(doorsSelected, d);
        addListener(windowsSelected, w);
        addListener(lightsSelected, l);

        update();
    }

    public void update(){
        if (Model.getSimulationParameters().isSimulationRunning()) {
            enableButtons();
        } else {
            disableButtons();
        }
    }

    private void addListener(Set<TreeItem<String>> h, CheckBoxTreeItem<String> root) {
        root.addEventHandler(CheckBoxTreeItem.checkBoxSelectionChangedEvent(), (CheckBoxTreeItem.TreeModificationEvent<String> evt) -> {
            CheckBoxTreeItem<String> item = evt.getTreeItem();
            if (evt.wasIndeterminateChanged()) {
                if (item.isIndeterminate()) {
                    h.remove(item);
                } else if (item.isSelected()) {
                    h.add(item);
                }
            } else if (evt.wasSelectionChanged()) {
                if (item.isSelected()) {
                    h.add(item);
                } else {
                    h.remove(item);
                }
            }
        });
    }

    private CheckBoxTreeItem<String> initializeTreeView(String type) {
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Rooms");
        root.setExpanded(true);

        ArrayList<Room> rooms = Model.getHouse().getRooms();

        for (Room room : rooms) {
            CheckBoxTreeItem<String> superItem = new CheckBoxTreeItem<>(room.getName());
            ArrayList<? extends Components> objectList = new ArrayList<>();
            switch (type) {
                case "Doors":
                    objectList = room.getDoors();
                    break;
                case "Windows":
                    objectList = room.getWindows();
                    break;
                case "Lights":
                    objectList = room.getLights();
                    break;
            }
            if (objectList.size() > 0) {
                for (Components itemT : objectList) {
                    CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(itemT.getName());
                    superItem.getChildren().add(item);
                }
            }
            root.getChildren().add(superItem);
        }
        return root;
    }

    @FXML
    private void openCloseDoors(ActionEvent actionEvent) {

        HashSet<Integer> doorsToUpdate = new HashSet<>();

        for (TreeItem<String> item : doorsSelected) {
            if (item.isLeaf() && item.getValue().contains("#")) {
                String value = item.getValue();
                doorsToUpdate.add(Integer.parseInt(value.split(" ")[1].replace("#", "")));
            }
        }
        shcModule.updateDoorState(doorsToUpdate);
    }

    @FXML
    private void openCloseWindows(ActionEvent actionEvent) {

        HashSet<Integer> windowsToUpdate = new HashSet<>();
        HashSet<Integer> lightsToUpdate = new HashSet<>();

        for (TreeItem<String> item : windowsSelected) {
            if (item.isLeaf() && item.getValue().contains("#")) {
                String value = item.getValue();
                windowsToUpdate.add(Integer.parseInt(value.split(" ")[1].replace("#", "")));
            }
        }
        shcModule.updateWindowState(windowsToUpdate);
    }

    @FXML
    private void openCloseLights(ActionEvent actionEvent) {

        HashSet<Integer> lightsToUpdate = new HashSet<>();

        for (TreeItem<String> item : lightsSelected) {
            if (item.isLeaf() && item.getValue().contains("#")) {
                String value = item.getValue();
                lightsToUpdate.add(Integer.parseInt(value.split(" ")[1].replace("#", "")));
            }
        }
        shcModule.updateLightState(lightsToUpdate);
    }

    public void ToggleAutoMode(ActionEvent actionEvent) {
        boolean success = shcModule.setAutoMode();
        boolean status = Model.getSimulationParameters().isAutoModeOn();
        autoModeButton.setText(status ? "Auto Mode ON" : "Auto Mode OFF");
        autoModeButton.setSelected(status);
    }

    public void disableButtons(){
        autoModeButton.setDisable(true);
        doorsTreeView.setDisable(true);
        windowsTreeView.setDisable(true);
        lightsTreeView.setDisable(true);
        doorsButton.setDisable(true);
        lightsButton.setDisable(true);
        windowsButton.setDisable(true);
    }

    public void enableButtons(){
        autoModeButton.setDisable(false);
        doorsTreeView.setDisable(false);
        windowsTreeView.setDisable(false);
        lightsTreeView.setDisable(false);
        doorsButton.setDisable(false);
        lightsButton.setDisable(false);
        windowsButton.setDisable(false);
    }

}
