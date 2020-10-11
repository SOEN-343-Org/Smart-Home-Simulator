package org.soen343.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class DashboardController  {

    public Label inputLbl;
    public HBox hbox;
    public TextField inputText;

    @FXML
    public void initialize() {
        // Initialization code can go here.
        inputLbl.setText("This is the dashboard");
    }

    public DashboardController() {
    }

    public void sendLabelText(ActionEvent actionEvent) {

    }

}
