package org.soen343.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.soen343.models.Model;
import org.soen343.services.ConsoleOutputService;
import org.soen343.services.DashboardService;
import org.soen343.services.SimulationContextService;
import org.soen343.services.modules.SHCModule;
import org.soen343.services.modules.SHPModule;
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
    @FXML
    private SmartHomeSecurityModuleController smartHomeSecurityModuleController;
    @FXML
    private SmartHomeHeatingModuleController smartHomeHeatingModuleController;
    @FXML
    private ConsoleOutputController consoleOutputController;

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
        smartHomeSecurityModuleController.setMainController(this);
        smartHomeHeatingModuleController.setMainController(this);
        simulationContextController.setMainController(this);
        simulationInfoController.setMainController(this);
        consoleOutputController.setMainController(this);

        // initialize controllers
        simulationContextController.initializeController();
        smartHomeSimulatorModuleController.initializeController();
        smartHomeCoreModuleController.initializeController();
        smartHomeSecurityModuleController.initializeController();
        smartHomeHeatingModuleController.initializeController();
        houseLayoutController.initializeController();
        simulationInfoController.initializeController();
        consoleOutputController.initializeController();

        // set observer pattern on views/controllers
        SimulationContextService.getInstance().attachObserver(houseLayoutController); // To update the blocked window in the
        SHSModule.getInstance().attachObserver(houseLayoutController) // To update individual location and more
                .attachObserver(simulationContextController) // To update individual location in the table
                .attachObserver(simulationInfoController) // To update time, date, temperature etc
                .attachObserver(smartHomeSimulatorModuleController); // To update the SHS table
        SHCModule.getInstance().attachObserver(houseLayoutController); // To update the modification to the house
        SHPModule.getInstance().attachObserver(smartHomeSecurityModuleController); // To update the visibility of the away mode
        DashboardService.getInstance().attachObserver(simulationContextController) // To update individual location
                .attachObserver(smartHomeSimulatorModuleController) // To update individual location
                .attachObserver(smartHomeCoreModuleController) // To update the visibility of the SHC tab
                .attachObserver(smartHomeSecurityModuleController); // To update the visibility of the SHP tab
        ConsoleOutputService.getInstance().attachObserver(consoleOutputController);
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

