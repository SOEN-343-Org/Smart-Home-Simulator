package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.models.house.Light;
import org.soen343.services.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class SHPModule extends Service {

    private static SHPModule shpModule = null;

    public static SHPModule getInstance() {
        if (shpModule == null) {
            shpModule = new SHPModule();
        }
        return shpModule;
    }

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
        // TODO: Make sure that we open the lights when their time comes

        if (Model.getSimulationParameters().isAwayModeOn()) {
            for (Light l : oldLights) {
                l.setOpen(false);
                System.out.println(l);
            }
        }
        Model.getSimulationParameters().getAwayModeParameters().setOpenLights(newLights);
        notifyObservers(this);
    }

    public void setLightsOpenTime(Date from, Date to) {
        if (from.after(to)) {
            // to is after from so we can update the time
            Model.getSimulationParameters().getAwayModeParameters().setLightsOpenFrom(from);
            Model.getSimulationParameters().getAwayModeParameters().setLightsOpenTo(to);
        }
    }


    public boolean setAwayMode() {
        //Look at permissions
        Individual currentIndividual = User.getCurrentIndividual();
        String role = currentIndividual.getRole();
        if (role.equals("Guest") || role.equals("Stranger")) {
            //TODO: Log that role cannot set away mode on/off
            return false;
        }
        if (!currentIndividual.getLocation().equals("outside")) {
            //TODO: Log that location cannot set away mode on/off
            return false;
        }
        Model.getSimulationParameters().setAwayMode();
        if (Model.getSimulationParameters().isAwayModeOn()) {
            SHCModule.getInstance().closeAllLights();
        }
        notifyObservers(this);
        return true;
    }
}
