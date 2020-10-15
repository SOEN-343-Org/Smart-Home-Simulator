package org.soen343.controllers;

import javafx.event.ActionEvent;

public class LoginController extends Controller {
    DashboardController mainController;

    public void changeScene(ActionEvent actionEvent) {
        mainController.exitLoginView();
    }
}
