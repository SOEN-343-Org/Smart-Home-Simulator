package org.soen343.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import org.soen343.services.modules.SHHModule;

import java.util.Set;

public class SmartHomeHeatingController extends Controller {

    @FXML
    private Pane smartHomeHeaterModule;
    @FXML
    private ToggleButton zoneModeButton;
    @FXML
    private TreeView<String> zone1TreeView;
    @FXML
    private TreeView<String> zone2TreeView;
    @FXML
    private TreeView<String> zone3TreeView;
    @FXML
    private TreeView<String> zone4TreeView;

    private SHHModule shhModule;

    private Set<TreeItem<String>> zone1Selected;
    private Set<TreeItem<String>> zone2Selected;
    private Set<TreeItem<String>> zone3Selected;
    private Set<TreeItem<String>> zone4Selected;

    @Override
    void initializeController() {

    }

    @Override
    void update() {

    }
}
