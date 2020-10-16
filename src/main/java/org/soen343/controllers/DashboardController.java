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
    private SmartHomeContextController smartHomeContextController;
    @FXML
    private LoginController loginController;
    @FXML
    private SimulationInfoController simulationInfoController;
    @FXML
    private SimulationContextController simulationContextController;
    @FXML
    private AnchorPane login;

    @FXML
    private AnchorPane simulationContext;

    @FXML
    public void initialize() {
        // Sets the model for controller superclass
        this.setModel(new Model());
        model.setIndividualsFromUser(model.user.getName()); //TODO: Add login logic to get the good username
        simulationInfoController.setMainController(this);
        smartHomeContextController.setMainController(this);
        smartHomeCoreController.setMainController(this);
        simulationContextController.setMainController(this);

        // init
        simulationContextController.init();
        smartHomeContextController.init();
        smartHomeCoreController.init();
        houseLayoutController.init();

        exitSimulationContext();

        drawLayout();
    }


    public void drawLayout() {
        houseLayoutController.drawLayout();
    }

    public void exitLoginView() {
        drawLayout();
        login.setVisible(false);
    }

    public void enterSimulationContext() {
        simulationContextController.updateInfo();
        simulationContext.setVisible(true);
    }

    public void exitSimulationContext() {
        drawLayout();
        simulationContext.setVisible(false);
    }
}
