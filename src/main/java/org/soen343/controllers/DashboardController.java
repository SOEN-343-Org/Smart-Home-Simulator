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
    private SimulationInfoController simulationInfoController;
    @FXML
    private AnchorPane login;

    @FXML
    private AnchorPane individual;

    @FXML
    public void initialize() {
        // Sets the model for controller superclass
        this.setModel(new Model());

        simulationInfoController.setMainController(this);
        individualController.setMainController(this);
        individualController.init();
        individual.setVisible(false); // We dont want to show the profile selection menu at the start

        houseLayoutController.init();

        drawLayout();
    }

    public void exitIndividualView() {
        drawLayout();
        individual.setVisible(false);
    }

    public void enterIndividualView() {
        individual.setVisible(true);
    }


    public void drawLayout() {
        houseLayoutController.drawLayout();
    }

    public void exitLoginView() {
        drawLayout();
        login.setVisible(false);
    }
}
