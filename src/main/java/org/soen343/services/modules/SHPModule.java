package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.models.house.Light;
import org.soen343.services.ConsoleOutputService;
import org.soen343.services.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class SHPModule extends Service {

    private static SHPModule shpModule = null;

    /**
     * Singleton instance
     * @return SHPModule instance
     */
    public static SHPModule getInstance() {
        if (shpModule == null) {
            shpModule = new SHPModule();
        }
        return shpModule;
    }

    /**
     * Set the lights during away mode
     * @param lightsToUpdate list of ind id
     */
    public void setAwayModeLights(HashSet<Integer> lightsToUpdate) {

        ArrayList<Light> oldLights = Model.getSimulationParameters().getAwayModeParameters().getOpenLights();
        ArrayList<Light> newLights = new ArrayList<>();
        for (int id : lightsToUpdate) {
            Light light = Model.getHouse().getLightById(id);
            newLights.add(light);
            oldLights.remove(light);
        }
        // Here old lights are lights we need to turn off
        // And new lights are lights that will be added to away mode parameters and be lit once their time comes

        SHCModule.getInstance().awayCloseLights(oldLights);
        Model.getSimulationParameters().getAwayModeParameters().setOpenLights(newLights);
    }

    /**
     * Set the time for the light to be open during away mode
     * @param from Date for starting date
     * @param to Date for ending date
     */
    public void setLightsOpenTime(Date from, Date to) {
        if (from.before(to)) {
            // to is after from so we can update the time
            Model.getSimulationParameters().getAwayModeParameters().setLightsOpenFrom(from);
            Model.getSimulationParameters().getAwayModeParameters().setLightsOpenTo(to);
        }
    }


    /**
     * Set the away mode
     * @return True if away mode is set else false
     */
    public boolean setAwayMode() {
        //Look at permissions

        Individual currentIndividual = User.getCurrentIndividual();
        // if away mode is on we want to turn it off even if there is still people inside
        if (Model.getSimulationParameters().isAwayModeOn()) {
            Model.getSimulationParameters().setAwayMode();
            ConsoleOutputService.getInstance().infoLog("[SHP Module] [Away Mode] " + currentIndividual.getName() + " has set Away Mode to OFF");
            SHHModule.getInstance().resetHVAC();
            notifyObservers(this);
            return true;
        }

        String role = currentIndividual.getRole();
        if (role.equals("Guest") || role.equals("Stranger")) {
            ConsoleOutputService.getInstance().warningLog("[SHP Module] [Away Mode] " + currentIndividual.getName() + " does not have the permission to set Away Mode");
            return false;
        }
        for (Individual ind : Model.getHouse().getIndividuals()) {
            if (!ind.getLocation().equals("outside")) {
                ConsoleOutputService.getInstance().warningLog("[SHP Module] [Away Mode] Cannot set Away Mode, " + ind.getName() + " is still in the house");
                return false;
            }
        }
        Model.getSimulationParameters().setAwayMode();
        ConsoleOutputService.getInstance().infoLog("[SHP Module] [Away Mode] " + currentIndividual.getName() + " has set Away Mode to " + (Model.getSimulationParameters().isAwayModeOn() ? "ON" : "OFF"));
        if (Model.getSimulationParameters().isAwayModeOn()) {
            SHCModule.getInstance().awayCloseLights(Model.getHouse().getAllLights());
            SHCModule.getInstance().awayCloseWindows(Model.getHouse().getAllWindows());
            SHCModule.getInstance().awayCloseDoors(Model.getHouse().getAllDoors());
            SHHModule.getInstance().resetHVAC();
        }
        notifyObservers(this);
        return true;
    }

    /**
     * Set the time before alerting the police
     * @param time seconds int
     */
    public void setTimeBeforeAlertingPolice(int time) {
        Model.getSimulationParameters().getAwayModeParameters().setTimeBeforeCallingPoliceAfterBreakIn(time);
    }

    /**
     * Intruder has been detected sets the timer to alert the authorities
     * @param individual Individual that broke into the hosue
     */
    public void intrusionDetectedDuringAwayMode(Individual individual) {
        SHCModule.getInstance().intrusionDetectedDuringAwayMode(individual);
        Calendar calendar = (Calendar) Model.getSimulationParameters().getDateTime().getDate().clone();
        calendar.add(Calendar.SECOND, Model.getSimulationParameters().getAwayModeParameters().getTimeBeforeCallingPoliceAfterBreakIn());
        Model.getSimulationParameters().getAwayModeParameters().setDateBeforeCallingPolice(calendar);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        ConsoleOutputService.getInstance().criticalLog("[SHP Module] [Away Mode] Alerting the authorities at " + timeFormat.format(calendar.getTime()));
    }

    /**
     * Called at each second to see if lights need to be updated and if it is time to call the authorities
     */
    public void notifiesTimeUpdate() {
        if (!Model.getSimulationParameters().isAwayModeOn()) {
            return;
        }
        // Away mode is on
        Date currentDate = Model.getSimulationParameters().getDateTime().getDate().getTime();
        Date from = Model.getSimulationParameters().getAwayModeParameters().getLightsOpenFrom();
        Date to = Model.getSimulationParameters().getAwayModeParameters().getLightsOpenTo();
        if (currentDate.after(from) && currentDate.before(to)) {
            // lights should be on during that time
            SHCModule.getInstance().awayOpenLights(Model.getSimulationParameters().getAwayModeParameters().getOpenLights());
        } else {
            // lights should be closed
            SHCModule.getInstance().awayCloseLights(Model.getSimulationParameters().getAwayModeParameters().getOpenLights());
        }
        Date authoritiesDate = Model.getSimulationParameters().getAwayModeParameters().getDateForCallingPoliceAfterBreakIn();

        if (currentDate.equals(authoritiesDate)) {
            // Time to alert the authorities
            SHCModule.getInstance().alertAuthorities();
        }
    }

    /**
     * Detect an intrusion
     * @param individual Individual that broke into the house
     * @param location Location where the individual is in
     */
    public void updateInIndividualLocation(Individual individual, String location) {
        if (Model.getSimulationParameters().isAwayModeOn()) {
            if (!location.equals("outside"))
                SHPModule.getInstance().intrusionDetectedDuringAwayMode(individual);
        }
    }

    /**
     * Turns off away mode when simulation is turned off
     */
    public void resetAwayMode() {
        Model.getSimulationParameters().setAwayMode();
        // Simulation is off while away mode was on, so we turn away mode off and reset the alerting authorities clock.
        Model.getSimulationParameters().getAwayModeParameters().setDateBeforeCallingPolice(null);
        ConsoleOutputService.getInstance().warningLog("[SHP Module] [Away Mode] Away Mode shut down because Simulation stopped");
        notifyObservers(this);
    }
}
