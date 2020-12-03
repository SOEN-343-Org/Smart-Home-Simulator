package org.soen343.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.services.DashboardService;
import org.soen343.services.modules.SHHModule;
import org.soen343.services.modules.SHPModule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SimulationInfoController extends Controller {

    @FXML
    private Slider slider;
    @FXML
    private Label summerTemp;
    @FXML
    private Label winterTemp;
    @FXML
    private Label currentMultiplier;
    @FXML
    private ToggleButton startStopToggle;
    @FXML
    private Label profileName;
    @FXML
    private Label role;
    @FXML
    private Label chosenLocation;
    @FXML
    private Label outsideTemp;
    @FXML
    private Label chosenDate;
    @FXML
    private Label chosenTime;

    private DashboardService dashboardService;

    private Timer timer = new Timer();

    public void initializeController() {
        dashboardService = DashboardService.getInstance();
        update();
        //initializing the listener
        addListenerToMultiplierSlider();
    }

    @FXML
    private void startSimulation(ActionEvent actionEvent) {
        boolean success = dashboardService.setSimulationRunning();
        boolean status = Model.getSimulationParameters().isSimulationRunning();
        startStopToggle.setText(status ? "ON" : "OFF");
        startStopToggle.setSelected(status);
        if (status) {
            Model.getSimulationParameters().getDateTime().startTime();
            startAnimatedTime();
        } else {
            Model.getSimulationParameters().getDateTime().stopTime();
            stopAnimatedTime();
        }
    }

    /**
     * Open simulation context view
     *
     * @param actionEvent
     */
    public void openSimulationContextView(ActionEvent actionEvent) {
        mainController.enterSimulationContext();
    }

    /**
     * Update profile name, role, chosen location and format time
     */
    @Override
    public void update() {
        Individual user = User.getCurrentIndividual();
        if (user == null) {
            profileName.setText("Profile not set");
            role.setText("Profile not set");
            chosenLocation.setText("Profile not set");
        } else {
            String getName = User.getCurrentIndividual().getName();
            profileName.setText(getName != null ? getName : "Profile not set");
            String getRole = User.getCurrentIndividual().getRole();
            role.setText(getRole != null ? getRole : "Profile not set");
            String getLocation = User.getCurrentIndividual().getLocation();
            chosenLocation.setText(getLocation);
        }
        updateTime();
        String summerTemp1 = String.format("%.2f", Model.getSimulationParameters().getSmartHeatingParameters().getSummerTemp());
        summerTemp.setText(summerTemp1 + " °C");
        String winterTemp1 = String.format("%.2f", Model.getSimulationParameters().getSmartHeatingParameters().getWinterTemp());
        winterTemp.setText(winterTemp1 + " °C");
        String temp = String.format("%.2f", Model.getSimulationParameters().getOutsideTemp());
        outsideTemp.setText(temp + " °C");
    }

    public void updateTime() {
        Date date = Model.getSimulationParameters().getDateTime().getDate().getTime();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        chosenTime.setText(timeFormat.format(date));
        chosenDate.setText(dateFormat.format(date));
    }

    private void startAnimatedTime() {
        timer = new Timer();
        SHPModule shpModule = SHPModule.getInstance();
        SHHModule shhModule = SHHModule.getInstance();
        Model.getHouse().setHavcStatus("START");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    shpModule.notifiesTimeUpdate();
                    shhModule.notifiesTimeUpdate();
                    updateTime();
                });
            }

        }, 0, (long) (Duration.ofSeconds(1).toMillis() / Model.getSimulationParameters().getDateTime().getClockSpeedMultiplier()));
    }

    private void stopAnimatedTime() {
        timer.cancel();
    }

    private void changeAnimatedTime() {
        if (Model.getSimulationParameters().isSimulationRunning()) {
            stopAnimatedTime();
            startAnimatedTime();
        }
    }

    private void addListenerToMultiplierSlider() {
        slider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    double value = (double) newValue;
                    double multiplier = ((double) (long) (value * 20 + 0.5)) / 20;
                    Model.getSimulationParameters().getDateTime().setClockSpeedMultiplier(multiplier);
                    currentMultiplier.setText("x " + multiplier);
                    changeAnimatedTime();
                }
        );
    }
}
