package org.soen343.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import org.soen343.models.Model;
import org.soen343.models.house.Room;
import org.soen343.models.house.Zone;
import org.soen343.services.modules.SHHModule;

import java.util.HashMap;
import java.util.Map;

public class SmartHomeHeatingModuleController extends Controller {
    @FXML
    private TableColumn zoneColumn;
    @FXML
    private TableColumn roomColumn;
    @FXML
    private TableColumn tempColumn;
    @FXML
    private Label currentZoneLabel;
    @FXML
    private ChoiceBox modifyZoneChoiceBox;
    @FXML
    private TextField morningTempTextField;
    @FXML
    private TextField afternoonTempTextField;
    @FXML
    private TextField nightTempTextField;
    @FXML
    private TableView zonesTableView;
    @FXML
    private TextField createZoneTextField;
    @FXML
    private ChoiceBox deleteZoneChoiceBox;
    @FXML
    private Button deleteZoneAction;
    @FXML
    private ChoiceBox addRoomToZoneChoiceBox;
    @FXML
    private ChoiceBox removeRoomFromZoneChoiceBox;
    @FXML
    private TextField overwriteTempTextField;
    @FXML
    private TextField summerTempTextField;
    @FXML
    private TextField winterTempTextField;

    private Zone currentZone;

    private SHHModule shhModule;

    @Override
    void initializeController() {
        currentZone = null;
        currentZoneLabel.setVisible(false);
        shhModule = SHHModule.getInstance();
        zoneColumn.setCellValueFactory(new MapValueFactory<>("zoneName"));
        roomColumn.setCellValueFactory(new MapValueFactory<>("roomsName"));
        tempColumn.setCellValueFactory(new MapValueFactory<>("desiredTemp"));



        setInformation();
    }

    @Override
    void update() {
        // Update table
        ObservableList<Map<String, Object>> items = FXCollections.observableArrayList();
        for (Zone zone : Model.getHouse().getZones()) {
            String zoneName = zone.getName();
            StringBuilder roomsName = new StringBuilder();
            for (int i = 0; i < zone.getRooms().size(); i++) {
                roomsName.append(zone.getRooms().get(i).getName());
                if (i + 1 < zone.getRooms().size())
                    roomsName.append(", ");
            }
            String roomsNames = roomsName.toString();
            String desiredTemp = shhModule.getDesiredTempFromZone(zone);
//            System.out.println(desiredTemp); // TO REMOV
//            String delimiter = "\\u00B0";
//            String[] tokensVal = desiredTemp.split(delimiter);
//            for(String token : tokensVal) {
//                System.out.println(token);
//            }
//            float i = Float.parseFloat(tokensVal[0]);
//            System.out.println(i + " is now an float number");
            Map<String, Object> item = new HashMap<>();
            item.put("zoneName", zoneName);
            item.put("roomsName", roomsNames);
            item.put("desiredTemp", desiredTemp);
            items.add(item);
        }
        zonesTableView.getItems().setAll(items);

        setInformation();
    }

    private void setInformation() {
        createZoneTextField.setText("");
        deleteZoneChoiceBox.setItems(FXCollections.observableArrayList(Model.getHouse().getZones()));

        summerTempTextField.setText(String.format("%.2f", Model.getSimulationParameters().getSmartHeatingParameters().getSummerTemp()));
        winterTempTextField.setText(String.format("%.2f", Model.getSimulationParameters().getSmartHeatingParameters().getWinterTemp()));

        modifyZoneChoiceBox.setItems(FXCollections.observableArrayList(Model.getHouse().getZones()));
        modifyZoneChoiceBox.setValue(currentZone);
    }

    private void updateZoneFields() {
        if (currentZone != null) {
            currentZoneLabel.setVisible(true);
            currentZoneLabel.setText(currentZone.getName());
            addRoomToZoneChoiceBox.setItems(FXCollections.observableArrayList(Model.getHouse().getRooms()));
            removeRoomFromZoneChoiceBox.setItems(FXCollections.observableArrayList(currentZone.getRooms()));
            morningTempTextField.setText(String.format("%.2f", currentZone.getMorningTemp()));
            afternoonTempTextField.setText(String.format("%.2f", currentZone.getAfternoonTemp()));
            nightTempTextField.setText(String.format("%.2f", currentZone.getNightTemp()));
            if (currentZone.getOverwritten())
                overwriteTempTextField.setText(String.format("%.2f", currentZone.getOverwrittenTemp()));
            else
                overwriteTempTextField.setText("");
        } else {
            currentZoneLabel.setVisible(false);
            addRoomToZoneChoiceBox.setItems(FXCollections.observableArrayList());
            removeRoomFromZoneChoiceBox.setItems(FXCollections.observableArrayList());
            morningTempTextField.setText("");
            afternoonTempTextField.setText("");
            nightTempTextField.setText("");
            overwriteTempTextField.setText("");
        }
    }

    @FXML
    private void setMorningTempAction(ActionEvent actionEvent) {
        if (isDouble(morningTempTextField.getText()) && currentZone != null) {
            shhModule.setMorningTempToZone(Double.parseDouble(morningTempTextField.getText()), currentZone);
        }
    }

    @FXML
    private void setAfternoonTempAction(ActionEvent actionEvent) {
        if (isDouble(afternoonTempTextField.getText()) && currentZone != null) {
            shhModule.setAfternoonTempToZone(Double.parseDouble(afternoonTempTextField.getText()), currentZone);
        }
    }

    @FXML
    private void setNightTempAction(ActionEvent actionEvent) {
        if (isDouble(nightTempTextField.getText()) && currentZone != null) {
            shhModule.setNightTempToZone(Double.parseDouble(nightTempTextField.getText()), currentZone);
        }
    }

    @FXML
    private void createZoneAction(ActionEvent actionEvent) {
        if (!createZoneTextField.getText().isBlank()) {
            shhModule.createZone(createZoneTextField.getText());
        }
    }

    // TODO : Fix errors
    @FXML
    private void deleteZoneAction(ActionEvent actionEvent) {
        Zone zone = (Zone) deleteZoneChoiceBox.getValue();
        if (zone != null) {
            if (zone.getName().equals(currentZone.getName())) {
                currentZone = null;
                updateZoneFields();
            }
            shhModule.deleteZone(zone);
        }
    }

    @FXML
    private void addRoomToZoneAction(ActionEvent actionEvent) {
        if (addRoomToZoneChoiceBox.getValue() != null) {
            Room r = (Room) addRoomToZoneChoiceBox.getValue();
            shhModule.addRoomToZone(r, currentZone);
            updateZoneFields();
        }
    }

    @FXML
    private void removeRoomFromZoneAction(ActionEvent actionEvent) {
        if (removeRoomFromZoneChoiceBox.getValue() != null) {
            Room r = (Room) removeRoomFromZoneChoiceBox.getValue();
            shhModule.removeRoomFromZone(r, currentZone);
            updateZoneFields();
        }
    }

    @FXML
    private void overwriteTempAction(ActionEvent actionEvent) {
        if (isDouble(overwriteTempTextField.getText()) && currentZone != null) {
            shhModule.setOverwriteTemp(Double.parseDouble(overwriteTempTextField.getText()), currentZone);
        }
    }

    @FXML
    private void removeOverwriteAction(ActionEvent actionEvent) {
        if (currentZone != null) {
            shhModule.removeOverwriteTemp(currentZone);
            updateZoneFields();
        }
    }

    @FXML
    private void setSummerTempAction(ActionEvent actionEvent) {
        if (isDouble(summerTempTextField.getText())) {
            shhModule.setSummerTemp(Double.parseDouble(summerTempTextField.getText()));
        }
    }

    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            //not a double
        }
        return false;
    }

    @FXML
    private void setWinterTempAction(ActionEvent actionEvent) {
        if (isDouble(winterTempTextField.getText())) {
            shhModule.setWinterTemp(Double.parseDouble(winterTempTextField.getText()));
        }
    }

    @FXML
    private void modifyZoneAction(ActionEvent actionEvent) {
        if (modifyZoneChoiceBox.getValue() != null) {
            currentZone = (Zone) modifyZoneChoiceBox.getValue();
            updateZoneFields();
        }
    }
}
