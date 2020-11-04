package org.soen343.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.AnchorPane;
import org.soen343.models.Model;
import org.soen343.models.house.Light;
import org.soen343.services.modules.SHPModule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SmartHomeSecurityModuleController extends Controller {
    public AnchorPane smartHomeSecurityModule;
    @FXML
    private AnchorPane awayModeParamPane;
    @FXML
    private ToggleButton awayMode;
    @FXML
    private Label errorTime;
    @FXML
    private TreeView<String> lightsTree;
    @FXML
    private Spinner<Integer> timeBeforeAlertingAuthoritiesSpinner;
    @FXML
    private TextField timeFrom;
    @FXML
    private TextField timeTo;

    private Set<TreeItem<String>> lightsSelected;

    private SHPModule shpModule;

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

    @Override
    void initializeController() {

        shpModule = SHPModule.getInstance();
        lightsSelected = new HashSet<>();
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Rooms");
        root.setExpanded(true);

        ArrayList<Light> lights = Model.getHouse().getAllLights();
        for (Light itemT : lights) {
            CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(itemT.getName());
            root.getChildren().add(item);
        }
        addListener(lightsSelected, root);

        lightsTree.setRoot(root);
        lightsTree.setShowRoot(false);
        lightsTree.setCellFactory(CheckBoxTreeCell.forTreeView());

        timeBeforeAlertingAuthoritiesSpinner.setEditable(true);
        update();
    }

    @Override
    void update() {

        if (!Model.getSimulationParameters().isSimulationRunning()) {
            if (Model.getSimulationParameters().isAwayModeOn())
                toggleAwayMode(null);
            smartHomeSecurityModule.setDisable(true);
            return;
        }
        smartHomeSecurityModule.setDisable(false);

        if (!Model.getSimulationParameters().isAwayModeOn()) {
            awayModeParamPane.setDisable(true);
            return;
        }
        awayModeParamPane.setDisable(false);

        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        timeFrom.setText(timeFormat.format(Model.getSimulationParameters().getAwayModeParameters().getLightsOpenFrom()));
        timeTo.setText(timeFormat.format(Model.getSimulationParameters().getAwayModeParameters().getLightsOpenTo()));

        timeBeforeAlertingAuthoritiesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1439, Model.getSimulationParameters().getAwayModeParameters().getTimeBeforeCallingPoliceAfterBreakIn()));

    }

    public void toggleAwayMode(ActionEvent actionEvent) {
        boolean success = shpModule.setAwayMode();
        boolean status = Model.getSimulationParameters().isAwayModeOn();
        awayMode.setText(status ? "Away Mode ON" : "Away Mode OFF");
        awayMode.setSelected(status);
    }

    public void setLightsTime(ActionEvent actionEvent) {
        if (timeFrom.getText().isBlank() || timeTo.getText().isBlank())
            return;

        Date from = parseTime(timeFrom.getText());
        Date to = parseTime(timeTo.getText());
        if (from == null || to == null) {
            errorTime.setVisible(true);
            return;
        }
        shpModule.setLightsOpenTime(from, to);
        errorTime.setVisible(false);
    }

    private Date parseTime(String t) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            return timeFormat.parse(t);
        } catch (ParseException ignored) {
        }
        return null;
    }


    public void setTimeBeforeAlertingAuthorities(ActionEvent actionEvent) {
        int time = timeBeforeAlertingAuthoritiesSpinner.getValue();
        shpModule.setTimeBeforeAlertingPolice(time);
    }

    public void setAwayModeLights(ActionEvent actionEvent) {
        HashSet<Integer> lightsToUpdate = new HashSet<>();

        for (TreeItem<String> item : lightsSelected) {
            if (item.isLeaf() && item.getValue().contains("#")) {
                String value = item.getValue();
                lightsToUpdate.add(Integer.parseInt(value.split(" ")[1].replace("#", "")));
            }
        }
        shpModule.setAwayModeLights(lightsToUpdate);
    }
}
