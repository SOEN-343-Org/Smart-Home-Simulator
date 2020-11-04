package org.soen343.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.soen343.models.permissions.*;

import java.util.ArrayList;

/**
 * The type Commands info controller.
 */
public class CommandsInfoController{

    @FXML
    private TableColumn shcCommandColumn;
    @FXML
    private TableColumn SHCpermissionsColumn;
    @FXML
    private TableColumn shpCommandColumn;
    @FXML
    private TableColumn SHPpermissionsColumn;
    @FXML
    private TableView SHCtable;
    @FXML
    private TableView SHPtable;


    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        setSHCTable();
        setSHPTable();
    }

    private void setSHCTable() {
        shcCommandColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SHCpermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        CommandsContract p1 = new SHCcommands();
        ArrayList SHCCommands = p1.getContract();

        SHCtable.setFixedCellSize(100.0);
        SHCtable.setItems(FXCollections.observableArrayList(SHCCommands));
    }

    private void setSHPTable() {
        shpCommandColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SHPpermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        CommandsContract p2 = new SHPcommands();
        ArrayList SHPCommands = p2.getContract();

        SHPtable.setFixedCellSize(100.0);
        SHPtable.setItems(FXCollections.observableArrayList(SHPCommands));
    }
}
