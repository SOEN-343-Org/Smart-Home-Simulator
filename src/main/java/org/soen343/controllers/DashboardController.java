package org.soen343.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.soen343.models.Model;
import org.soen343.models.parameters.SimulationParameters;
import org.soen343.services.DashboardService;
import org.soen343.services.SimulationContextService;
import org.soen343.services.modules.SHCModule;
import org.soen343.services.modules.SHSModule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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

    //Info Box Variables
    @FXML
    public Text currentDate;
    public Text currentTime;
    public Text currentMultiplier;
    public Slider slider;

    private Timer timer;
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

        //intialize listener
        addListenerToMultiplierSlider();

        // set observer pattern on views/controllers
        SimulationContextService.getInstance().attachObserver(houseLayoutController).attachObserver(simulationContextController).attachObserver(simulationInfoController).attachObserver(smartHomeSimulatorModuleController);
        SHSModule.getInstance().attachObserver(houseLayoutController).attachObserver(simulationContextController).attachObserver(simulationInfoController).attachObserver(smartHomeSimulatorModuleController);
        SHCModule.getInstance().attachObserver(houseLayoutController).attachObserver(simulationContextController).attachObserver(simulationInfoController).attachObserver(smartHomeSimulatorModuleController);

        // hide simulation context window
        exitSimulationContext();
        houseLayoutController.update();
    }

    public void updateTime(Calendar calendar) {
        Date date = calendar.getTime();
        DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        currentTime.setText(timeFormat.format(date));
        currentDate.setText(dateFormat.format(date));
    }
    private void startAnimatedTime() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateTime(SimulationParameters.dateTime.getDate()));
            }
        },0, (long) (Duration.ofSeconds(1).toMillis()/SimulationParameters.dateTime.getClockSpeedMultiplier()));
    }

    private void stopAnimatedTime() {
        timer.cancel();
    }

    private void changeAnimatedTime() {
        stopAnimatedTime();
        startAnimatedTime();
    }

    private void addListenerToMultiplierSlider() {
        slider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    double multiplier = Math.round((Double) newValue);
                    SimulationParameters.dateTime.setClockSpeedMultiplier(multiplier);
                    currentMultiplier.setText(String.valueOf(multiplier));
                    changeAnimatedTime();
                }
        );
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

