package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;
import org.soen343.models.house.Room;
import org.soen343.models.house.Window;
import org.soen343.models.house.Zone;
import org.soen343.services.ConsoleOutputService;
import org.soen343.services.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;

public class SHHModule extends Service {
    private static SHHModule shhModule = null;

    public static SHHModule getInstance() {
        if (shhModule == null) {
            shhModule = new SHHModule();
        }
        return shhModule;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
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

    private String getUserName() {
        Individual ind = User.getCurrentIndividual();
        return ind == null ? "Admin" : ind.getName();
    }

    private boolean validateUserPermission() {
        Individual user = User.getCurrentIndividual();
        if (user == null)
            return true;
        String role = user.getRole();
        return role.equals("Family Adult");
    }

    public void createZone(String text) {
        if (validateUserPermission()) {
            Model.getHouse().addZone(new Zone(text));
            notifyObservers(this);
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " has created Zone #" + text);
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to create a zone");
    }

    public void deleteZone(Zone zone) {
        if (validateUserPermission()) {
            Model.getHouse().removeZone(zone);
            for (Room r : zone.getRooms()) {
                r.getHeater().setOn(false);
                r.getAC().setOn(false);
                r.setHvacState(false);
            }
            notifyObservers(this);
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " has removed Zone #" + zone);
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to delete a zone");
    }

    public void setSummerTemp(double parseDouble) {
        if (validateUserPermission()) {
            Model.getSimulationParameters().getSmartHeatingParameters().setSummerTemp(parseDouble);
            resetHVAC();
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " has set the summer temperature to " + parseDouble + " °C");
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to set the summer temperature");
    }

    public void setWinterTemp(double parseDouble) {
        if (validateUserPermission()) {
            Model.getSimulationParameters().getSmartHeatingParameters().setWinterTemp(parseDouble);
            resetHVAC();
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " has set the winter temperature to " + parseDouble + " °C");
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to set the winter temperature");
    }

    public void addRoomToZone(Room r, Zone currentZone) {
        if (validateUserPermission()) {
            Model.getHouse().addRoomToZone(r, currentZone);
            notifyObservers(this);
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " added the " + r + " to Zone #" + currentZone);
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to add a room to the zone");
    }

    public void removeRoomFromZone(Room r, Zone currentZone) {
        if (validateUserPermission()) {
            Model.getHouse().removeRoomFromZone(r, currentZone);
            r.setHvacState(false);
            r.getAC().setOn(false);
            r.getHeater().setOn(false);
            closeWindows(r);
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " removed the " + r + " from Zone #" + currentZone);
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to remove a room to the zone");
    }

    public void setMorningTempToZone(double parseDouble, Zone currentZone) {
        if (validateUserPermission()) {
            currentZone.setMorningTemp(parseDouble);
            resetHVAC();
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " has set morning temperature of " + parseDouble + " °C to Zone #" + currentZone);
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to set morning temperature to zone");
    }

    public void setAfternoonTempToZone(double parseDouble, Zone currentZone) {
        if (validateUserPermission()) {
            currentZone.setAfternoonTemp(parseDouble);
            resetHVAC();
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " has set afternoon temperature of " + parseDouble + " °C to Zone #" + currentZone);
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to set afternoon temperature to zone");
    }

    public void setNightTempToZone(double parseDouble, Zone currentZone) {
        if (validateUserPermission()) {
            currentZone.setNightTemp(parseDouble);
            resetHVAC();
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " has set night temperature of " + parseDouble + " °C to Zone #" + currentZone);
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to set night temperature to zone");
    }

    public void setOverwriteTemp(double parseDouble, Zone currentZone) {
        if (validateUserPermission()) {
            currentZone.setOverwrittenTemp(parseDouble);
            resetHVAC();
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " has overwritten the temperature of Zone #" + currentZone + " to " + parseDouble + " °C");
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to overwrite temperature");
    }

    public void removeOverwriteTemp(Zone currentZone) {
        if (validateUserPermission()) {
            currentZone.setOverwritten(false);
            resetHVAC();
            ConsoleOutputService.getInstance().infoLog("[SHH Module] " + getUserName() + " has removed the overwritten temperature of Zone #" + currentZone);
        } else
            ConsoleOutputService.getInstance().warningLog("[SHH Module] " + getUserName() + " does not have the permission to remove overwrite temperature");
    }

    /**
     * This is a giga method for giga updates
     * and is of giga proportions
     */
    private void gigaUpdate() {

        // Find which temperature is desired
        // Loop over each zone and find context of desired temperature

        // 1. if awaymode is on
        // 1.1 if overwritten
        // 1.1.1 change temp to summer overwritten
        // 1.1.2 change temp to winter overwritten

        // 1.2 not overwritten
        // 1.2.1 change temp to summer away (cannot open windows)
        // 1.2.2 change temp to winter away (cannot open windows)

        // 2. (away mode off)
        // 2.1 if overwritten
        // 2.1.1 change temp to summer overwritten
        // 2.1.2 change temp to winter overwritten

        // 2.2 not overwritten
        // 2.2.1 morning temp
        // 2.2.1.1 change temp to summer
        // 2.2.1.2 change temp to winter (cannot open windows)

        // 2.2.2 afternoon temp
        // 2.2.2.1 change temp to summer
        // 2.2.2.2 change temp to winter (cannot open windows)

        // 2.2.3 night temp
        // 2.2.3.1 change temp to summer
        // 2.2.3.2 change temp to winter (cannot open windows)


        ArrayList<Zone> zones = Model.getHouse().getZones();

        // 1. if awaymode is on

        if (Model.getSimulationParameters().isAwayModeOn()) {
            for (Zone zone : zones) {

                // Look if zone is overwritten
                int currentMonth = Model.getSimulationParameters().getDateTime().getDate().get(Calendar.MONTH);
                if (zone.getOverwritten()) {
                    double desiredTemp = zone.getOverwrittenTemp();
                    if (Model.getSimulationParameters().getSmartHeatingParameters().isSummer(currentMonth)) {
                        updateZoneTemp("summer", desiredTemp, zone);
                    } else {
                        updateZoneTemp("winter", desiredTemp, zone);
                    }
                } else {
                    // 1.1 Summer temp
                    // ...
                    // 1.2 Winter temp
                    // ...

                    if (Model.getSimulationParameters().getSmartHeatingParameters().isSummer(currentMonth)) {
                        // Summer temp
                        double desiredTemp = Model.getSimulationParameters().getSmartHeatingParameters().getSummerTemp();
                        updateZoneTemp("away", desiredTemp, zone);
                    } else {
                        // Winter temp
                        double desiredTemp = Model.getSimulationParameters().getSmartHeatingParameters().getWinterTemp();
                        updateZoneTemp("winter", desiredTemp, zone); // Because we dont open windows in winter, we can just take that function
                    }
                }
            }
        } else {
            // 2. (away mode off) Check moment of day
            for (Zone zone : zones) {

                // Look if zone is overwritten
                int currentMonth = Model.getSimulationParameters().getDateTime().getDate().get(Calendar.MONTH) + 1;
                if (zone.getOverwritten()) {
                    double desiredTemp = zone.getOverwrittenTemp();
                    if (Model.getSimulationParameters().getSmartHeatingParameters().isSummer(currentMonth)) {
                        updateZoneTemp("summer", desiredTemp, zone);
                    } else {
                        updateZoneTemp("winter", desiredTemp, zone);
                    }
                } else {
                    // 2.1 morning
                    // ...
                    // 2.2 afternoon
                    // ...
                    // 2.3 night
                    // ...
                    if (Model.getSimulationParameters().getSmartHeatingParameters().isSummer(currentMonth)) {
                        // Summer temp
                        // Check moment of day
                        int currentHour = Model.getSimulationParameters().getDateTime().getDate().get(Calendar.HOUR_OF_DAY);
                        String timePeriod = Model.getSimulationParameters().getSmartHeatingParameters().getTimePeriod(currentHour);
                        double desiredTemp;
                        switch (timePeriod) {
                            case "morning":
                                desiredTemp = zone.getMorningTemp();
                                updateZoneTemp("summer", desiredTemp, zone);
                                break;
                            case "afternoon":
                                desiredTemp = zone.getAfternoonTemp();
                                updateZoneTemp("summer", desiredTemp, zone);
                                break;
                            case "night":
                                desiredTemp = zone.getNightTemp();
                                updateZoneTemp("summer", desiredTemp, zone);
                                break;
                        }
                    } else {
                        // Winter temp
                        // Check moment of day
                        int currentHour = Model.getSimulationParameters().getDateTime().getDate().get(Calendar.HOUR_OF_DAY);
                        String timePeriod = Model.getSimulationParameters().getSmartHeatingParameters().getTimePeriod(currentHour);
                        double desiredTemp;
                        switch (timePeriod) {
                            case "morning":
                                desiredTemp = zone.getMorningTemp();
                                updateZoneTemp("winter", desiredTemp, zone);
                                break;
                            case "afternoon":
                                desiredTemp = zone.getAfternoonTemp();
                                updateZoneTemp("winter", desiredTemp, zone);
                                break;
                            case "night":
                                desiredTemp = zone.getNightTemp();
                                updateZoneTemp("winter", desiredTemp, zone);
                                break;
                        }
                    }
                }
            }
        }

        // Update all rooms that are in no zones, like HVAC stopped, temperature will go towards outside temp
        updateNoZoneTemp();

        // Look at 0 degree rooms and alert of possible pipe explosion
        alertColdRooms();
    }

    private void alertColdRooms() {
        for (Room r : Model.getHouse().getRooms()) {
            if (r.getTemperature() <= 0.01 && r.getTemperature() >= -0.06) {
                ConsoleOutputService.getInstance().warningLog("[SHS Module] ALERT - Temperature of 0°C has been reach in room: " + r.getName() + ". Potential danger of pipe burst.");
            }
        }
    }

    private ArrayList<String> getAllZoneRoomNames() {
        ArrayList<String> allZoneRoomNames = new ArrayList<>();
        for (Zone zone : Model.getHouse().getZones()) {
            for (Room room : zone.getRooms()) {
                allZoneRoomNames.add(room.getName());
            }
        }
        return allZoneRoomNames;
    }

    private void updateNoZoneTemp() {
        ArrayList<String> allZoneRooms = getAllZoneRoomNames();

        ArrayList<String> allRoomNames = new ArrayList<>();

        for (Room room : Model.getHouse().getRooms()) {
            allRoomNames.add(room.getName());
        }

        for (String roomName : allRoomNames) {
            if (!allZoneRooms.contains(roomName)) {
                adjustToOutsideTemp(Model.getHouse().getRoomByName(roomName),
                        Model.getSimulationParameters().getOutsideTemp(),
                        Model.getHouse().getRoomByName(roomName).getTemperature()
                );
            }
        }
    }

    private void updateZoneTemp(String state, double desiredTemp, Zone zone) {
        for (Room room : zone.getRooms()) {
            double currentTemp = room.getTemperature();
            if (room.isHvacState()) { // Means the temperature is not the same as desired, hvac is on
                if (currentTemp < desiredTemp) { // Need to increase the temperature
                    switch (state) {
                        case "summer":
                            increaseTempSummer(room, currentTemp);
                            break;
                        case "winter":
                            increaseTempWinter(room, currentTemp);
                            break;
                        case "away":
                            increaseTempSummerAway(room, currentTemp);
                            break;
                    }
                } else if (currentTemp > desiredTemp) { // Need to decrease temperature
                    switch (state) {
                        case "summer":
                            decreaseTempSummer(room, currentTemp);
                            break;
                        case "winter":
                            decreaseTempWinter(room, currentTemp);
                            break;
                        case "away":
                            decreaseTempSummerAway(room, currentTemp);
                            break;
                    }
                }
                if (room.getTemperature() >= desiredTemp - 0.06 && room.getTemperature() <= desiredTemp + 0.06) {
                    room.getHeater().setOn(false);
                    room.getAC().setOn(false);
                    room.setHvacState(false);
                    closeWindows(room);
                }
            } else { // hvac is off, temperature goes toward outside
                double outsideTemp = Model.getSimulationParameters().getOutsideTemp();
                adjustToOutsideTemp(room, outsideTemp, currentTemp);

                if (room.getTemperature() <= desiredTemp - 0.24 || room.getTemperature() >= desiredTemp + 0.24)
                    room.setHvacState(true);
            }
        }
    }

    private void adjustToOutsideTemp(Room room, double outsideTemp, double currentTemp) {
        outsideTemp = round(outsideTemp, 2);
        currentTemp = round(currentTemp, 2);

        if (currentTemp > outsideTemp) { // must decrease
            room.setTemperature(currentTemp - 0.05);
        } else if (currentTemp < outsideTemp) { // must increase
            room.setTemperature(currentTemp + 0.05);
        }
    }

    private void decreaseTempSummer(Room room, double currentTemp) {
        if (currentTemp >= Model.getSimulationParameters().getOutsideTemp()) {
            if (canOpenWindow(room)) {
                openWindows(room);
            } else {
                room.getAC().setOn(true);
            }
        } else {
            room.getAC().setOn(true);
        }
        room.setTemperature(currentTemp - 0.1);
    }

    private void increaseTempSummer(Room room, double currentTemp) {
        if (currentTemp >= Model.getSimulationParameters().getOutsideTemp()) {
            room.getHeater().setOn(true);
        } else {
            if (canOpenWindow(room)) {
                openWindows(room);
            } else {
                room.getHeater().setOn(true);
            }
        }
        room.setTemperature(currentTemp + 0.1);
    }

    private void decreaseTempSummerAway(Room room, double currentTemp) {
        room.getAC().setOn(true);
        room.setTemperature(currentTemp - 0.1);
    }

    private void increaseTempSummerAway(Room room, double currentTemp) {
        room.getHeater().setOn(true);
        room.setTemperature(currentTemp + 0.1);
    }

    private void decreaseTempWinter(Room room, double currentTemp) {
        room.getAC().setOn(true);
        room.setTemperature(currentTemp - 0.1);
    }

    private void increaseTempWinter(Room room, double currentTemp) {
        room.getHeater().setOn(true);
        room.setTemperature(currentTemp + 0.1);
    }

    private boolean canOpenWindow(Room room) {
        ArrayList<Window> windows = room.getWindows();
        if (windows.size() > 0) {
            for (Window w : windows) {
                if (!w.isBlocked()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void notifiesTimeUpdate() {
        // This is called every second and needs to update the temperature
        gigaUpdate();
        notifyObservers(this);
    }

    public void openWindows(Room r) {
        ArrayList<Window> windows = r.getWindows();

        for (Window w : windows) {
            if (!w.isBlocked()) {
                w.setOpen(true);
            }
        }
    }

    public void closeWindows(Room r) {
        ArrayList<Window> windows = r.getWindows();

        for (Window w : windows) {
            if (!w.isBlocked()) {
                w.setOpen(false);
            }
        }
    }


    public void resetHVAC() {
        for (Room r : Model.getHouse().getRooms()) {
            r.getHeater().setOn(false);
            r.getAC().setOn(false);
            r.setHvacState(false);
            closeWindows(r);
        }
        notifyObservers(this);
    }
}
