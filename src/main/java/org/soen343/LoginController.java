package org.soen343;

import javafx.event.ActionEvent;
import org.soen343.controllers.DashboardController;

public class LoginController {
    DashboardController mainC;

    public void setMain(DashboardController c) {
        mainC = c;
    }

    public void changeScene(ActionEvent actionEvent) {
        mainC.login();
    }
}
