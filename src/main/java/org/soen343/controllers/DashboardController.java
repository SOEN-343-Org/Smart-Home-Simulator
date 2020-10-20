package org.soen343.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.soen343.services.DashboardService;

public class DashboardController extends Controller {


    @FXML
    private HouseLayoutController houseLayoutController;
    @FXML
    private SmartHomeCoreModuleController smartHomeCoreModuleController;
    @FXML
    private SmartHomeSimulatorModuleController smartHomeSimulatorModuleController;
    @FXML
    private SimulationInfoController simulationInfoController;
    @FXML
    private SimulationContextController simulationContextController;

    // UI element to change view
    @FXML
    private AnchorPane simulationContext;

    /**
     * Initialize controllers
     */
    public void initialize() {
        DashboardService dashboardService = new DashboardService();
        dashboardService.populateIndividuals();

        simulationInfoController.setMainController(this);
        smartHomeSimulatorModuleController.setMainController(this);
        smartHomeCoreModuleController.setMainController(this);
        simulationContextController.setMainController(this);
        simulationInfoController.setMainController(this);

        // initialize controllers
        simulationContextController.initializeController();
        smartHomeSimulatorModuleController.initializeController();
        smartHomeCoreModuleController.initializeController();
        houseLayoutController.initializeController();
        simulationInfoController.initializeController();

        // hide simulation context window
        exitSimulationContext();
        update();
    }

    /**
     * Update
     */
    public void update() {
        smartHomeSimulatorModuleController.update();
        simulationInfoController.update();
        houseLayoutController.drawLayout();
    }

    /**
     * Enter simulation
     */
    public void enterSimulationContext() {
        simulationContextController.initializeController();
        simulationContext.setVisible(true);
    }

    /**
     * Exit simulation
     */
    public void exitSimulationContext() {
        update();
        simulationContext.setVisible(false);
    }

    @Override
    void initializeController() {

    }
}
