package org.soen343.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.soen343.models.permissions.PermissionsContract;
import org.soen343.models.permissions.SHCpermissions;
import org.soen343.models.permissions.SHPpermissions;

import java.util.ArrayList;

public class CommandsInfoController{

    @FXML
    private TableColumn SHCprofileColumn;
    @FXML
    private TableColumn SHCpermissionsColumn;
    @FXML
    private TableColumn SHPprofileColumn;
    @FXML
    private TableColumn SHPpermissionsColumn;
    @FXML
    private TableView SHCtable;
    @FXML
    private TableView SHPtable;


    @FXML
    public void initialize() {
        setSHCTable();
        setSHPTable();
    }

    private void setSHCTable() {
        SHCprofileColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        SHCpermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        PermissionsContract p1 = new SHCpermissions();
        ArrayList SHCPermissions = p1.getContract();

        SHCtable.setFixedCellSize(60.0);
        SHCtable.setItems(FXCollections.observableArrayList(SHCPermissions));
    }

    private void setSHPTable() {
        SHPprofileColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        SHPpermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        PermissionsContract p2 = new SHPpermissions();
        ArrayList SHPPermissions = p2.getContract();

        SHPtable.setFixedCellSize(60.0);
        SHPtable.setItems(FXCollections.observableArrayList(SHPPermissions));
    }
}
