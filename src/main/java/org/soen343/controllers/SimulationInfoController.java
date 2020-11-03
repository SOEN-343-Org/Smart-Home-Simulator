package org.soen343.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.services.DashboardService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        LocalDate date = Model.getSimulationParameters().getDateTime().getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String formattedDate = date.format(formatter);
        String hours = Integer.toString(Model.getSimulationParameters().getDateTime().getHours());
        String min = Integer.toString(Model.getSimulationParameters().getDateTime().getMinutes());
        String sec = Integer.toString(Model.getSimulationParameters().getDateTime().getSeconds());
        hours = hours.length() == 1 ? "0" + hours : hours;
        min = min.length() == 1 ? "0" + min : min;
        sec = sec.length() == 1 ? "0" + sec : sec;
        String formattedTime = hours + " : " + min + " : " + sec;
        chosenDate.setText(formattedDate);
        chosenTime.setText(formattedTime);

        String temp = Integer.toString(Model.getSimulationParameters().getOutsideTemp());
        outsideTemp.setText(temp + " °C");
    }
}
