package org.soen343.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.services.DashboardService;
import org.soen343.services.modules.SHPModule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SimulationInfoController extends Controller {

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
    private Timer clockTimer = new Timer();

    public void initializeController() {
        dashboardService = DashboardService.getInstance();
        update();
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
        // Format Date and Time
        Date date = Model.getSimulationParameters().getDateTime().getCalendar().getTime();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        chosenDate.setText(timeFormat.format(date));
        chosenTime.setText(dateFormat.format(date));

        String temp = Integer.toString(Model.getSimulationParameters().getOutsideTemp());
        outsideTemp.setText(temp + " °C");
    }

    public void updateTime() {
        Date date = Model.getSimulationParameters().getDateTime().getCalendar().getTime();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        chosenDate.setText(timeFormat.format(date));
        chosenTime.setText(dateFormat.format(date));
    }

    private void startAnimatedTime() {
        clockTimer = new Timer();
        SHPModule shpModule = SHPModule.getInstance();
        clockTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    shpModule.notifiesTimeUpdate();
                    updateTime();
                });
            }
        }, 0, (long) (Duration.ofSeconds(1).toMillis()));
    }

    private void stopAnimatedTime() {
        clockTimer.cancel();
    }

    private void changeAnimatedTime() {
        stopAnimatedTime();
        startAnimatedTime();
    }
}
