package org.soen343.controllers;

import javafx.event.ActionEvent;
import org.soen343.controllers.Controller;
import org.soen343.controllers.DashboardController;

public class LoginController extends Controller {
    DashboardController mainC;

    public void setMain(DashboardController c) {
        mainC = c;
    }

    public void changeScene(ActionEvent actionEvent) {
        mainC.login();
    }
}
