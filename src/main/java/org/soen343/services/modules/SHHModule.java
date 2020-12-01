package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.house.Room;
import org.soen343.models.house.Zone;
import org.soen343.services.Service;

import java.util.Calendar;

public class SHHModule extends Service {
    private static SHHModule shhModule = null;

    public static SHHModule getInstance() {
        if (shhModule == null) {
            shhModule = new SHHModule();
        }
        return shhModule;
    }

    public String getDesiredTempFromZone(Zone zone) {
        String t = "";

        if (zone.getOverwritten()) {
            t = String.format("%.2f", zone.getOverwrittenTemp()) + "°C - Overwritten";
        } else {
            Calendar currentDate = Model.getSimulationParameters().getDateTime().getDate();
            // Check for auto mode
            if (Model.getSimulationParameters().isAwayModeOn()) {
                // check summer
                int currentMonth = currentDate.get(Calendar.MONTH);
                if (Model.getSimulationParameters().getSmartHeatingParameters().isSummer(currentMonth)) {
                    t = String.format("%.2f", Model.getSimulationParameters().getSmartHeatingParameters().getSummerTemp()) + "°C - (Away) Summer temp";
                } else {
                    t = String.format("%.2f", Model.getSimulationParameters().getSmartHeatingParameters().getWinterTemp()) + "°C - (Away) Winter temp";
                }
            } else {
                // Check moment of day
                int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
                String timePeriod = Model.getSimulationParameters().getSmartHeatingParameters().getTimePeriod(currentHour);
                switch (timePeriod) {
                    case "morning":
                        t = String.format("%.2f", zone.getMorningTemp()) + "°C - Morning temp";
                        break;
                    case "afternoon":
                        t = String.format("%.2f", zone.getAfternoonTemp()) + "°C - Afternoon temp";
                        break;
                    case "night":
                        t = String.format("%.2f", zone.getNightTemp()) + "°C - Night temp";
                        break;
                }
            }
        }

        return t;
    }

    public void createZone(String text) {
        Model.getHouse().addZone(new Zone(text));
        notifyObservers(this);
    }

    public void deleteZone(Zone zone) {
        Model.getHouse().removeZone(zone);
        notifyObservers(this);
    }

    public void setSummerTemp(double parseDouble) {
        Model.getSimulationParameters().getSmartHeatingParameters().setSummerTemp(parseDouble);
        notifyObservers(this);
    }

    public void setWinterTemp(double parseDouble) {
        Model.getSimulationParameters().getSmartHeatingParameters().setWinterTemp(parseDouble);
        notifyObservers(this);
    }

    public void addRoomToZone(Room r, Zone currentZone) {
        Model.getHouse().addRoomToZone(r, currentZone);
        notifyObservers(this);
    }

    public void removeRoomFromZone(Room r, Zone currentZone) {
        Model.getHouse().removeRoomFromZone(r, currentZone);
        notifyObservers(this);
    }

    public void setMorningTempToZone(double parseDouble, Zone currentZone) {
        currentZone.setMorningTemp(parseDouble);
        notifyObservers(this);
    }

    public void setAfternoonTempToZone(double parseDouble, Zone currentZone) {
        currentZone.setAfternoonTemp(parseDouble);
        notifyObservers(this);
    }

    public void setNightTempToZone(double parseDouble, Zone currentZone) {
        currentZone.setNightTemp(parseDouble);
        notifyObservers(this);
    }

    public void setOverwriteTemp(double parseDouble, Zone currentZone) {
        currentZone.setOverwrittenTemp(parseDouble);
        notifyObservers(this);
    }

    public void removeOverwriteTemp(Zone currentZone) {
        currentZone.setOverwritten(false);
        notifyObservers(this);

    }

    public void notifiesTimeUpdate() {
        // This is called every second and needs to update the temperature

        



        notifyObservers(this);

    }
}
