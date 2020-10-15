package org.soen343.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.soen343.LoginController;
import org.soen343.models.Model;

public class DashboardController extends Controller {


    @FXML
    private HouseLayoutController houseLayoutController;
    @FXML
    private SmartHomeCoreController smartHomeCoreController;
    @FXML
    private LoginController loginController;

    @FXML
    private AnchorPane login;

    @FXML
    public void initialize() {
        this.setModel(new Model());
        houseLayoutController.setModel(model);
        smartHomeCoreController.setModel(model);
        loginController.setModel(model);

        houseLayoutController.drawLayout();

        loginController.setMain(this);

    }

    public void login() {
        login.setVisible(false);
    }
}
