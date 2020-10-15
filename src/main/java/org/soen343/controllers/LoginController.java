package org.soen343.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController extends Controller {
    DashboardController mainController;

    @FXML
    TextField signUpUsername;
    @FXML
    TextField signUpPassword;
    @FXML
    TextField signUpButton;

    public void changeScene(ActionEvent actionEvent) {
        mainController.exitLoginView();
    }
}
