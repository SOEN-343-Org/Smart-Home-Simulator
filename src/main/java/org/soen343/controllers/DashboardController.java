package org.soen343.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.soen343.models.Model;

public class DashboardController extends Controller {


    @FXML
    private HouseLayoutController houseLayoutController;
    @FXML
    private SmartHomeCoreController smartHomeCoreController;
    @FXML
    private LoginController loginController;
    @FXML
    private IndividualController individualController;

    @FXML
    private AnchorPane login;

    @FXML
    private AnchorPane individual;

    @FXML
    public void initialize() {
        // Sets the model for controller superclass
        this.setModel(new Model());

        individualController.setMainController(this);
        individualController.init();


        //loginController.setMain(this);
        houseLayoutController.init();

        drawLayout();
    }

    public void setIndividualViewVisibility(boolean vis) {
        drawLayout();
        individual.setVisible(vis);
    }

    public void drawLayout() {
        houseLayoutController.drawLayout();
    }

    public void login() {
        drawLayout();
        login.setVisible(false);
    }
}
