package org.soen343.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.soen343.models.Model;
import org.soen343.services.DashboardService;
import org.soen343.services.SimulationContextService;
import org.soen343.services.modules.SHCModule;
import org.soen343.services.modules.SHSModule;


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
        Model.setModelParameters();

        // Set the observer pattern of the modules onto the controllers
        // Modules are static so we give them references to controller objects and they update the controllers accordingly
        DashboardService.getInstance().populateIndividuals();

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

        // set observer pattern on views/controllers
        SimulationContextService.getInstance().attachObserver(houseLayoutController).attachObserver(simulationContextController).attachObserver(simulationInfoController).attachObserver(smartHomeSimulatorModuleController);
        SHSModule.getInstance().attachObserver(houseLayoutController).attachObserver(simulationContextController).attachObserver(simulationInfoController).attachObserver(smartHomeSimulatorModuleController);
        SHCModule.getInstance().attachObserver(houseLayoutController).attachObserver(simulationContextController).attachObserver(simulationInfoController).attachObserver(smartHomeSimulatorModuleController);

        // hide simulation context window
        exitSimulationContext();
        houseLayoutController.update();
    }

    /**
     * Update
     */
    @Override
    public void update() {
    }

    /**
     * Enter simulation
     */
    public void enterSimulationContext() {
        simulationContext.setVisible(true);
    }

    /**
     * Exit simulation
     */
    public void exitSimulationContext() {
        simulationContext.setVisible(false);
    }

    @Override
    void initializeController() {

    }
}

