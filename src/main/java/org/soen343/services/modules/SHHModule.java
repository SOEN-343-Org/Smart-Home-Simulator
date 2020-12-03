package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.house.Room;
import org.soen343.models.house.Window;
import org.soen343.models.house.Zone;
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
        for (Room r : zone.getRooms()) {
            r.getHeater().setOn(false);
            r.getAC().setOn(false);
            r.setHvacState(false);
        }
        notifyObservers(this);
    }

    public void setSummerTemp(double parseDouble) {
        Model.getSimulationParameters().getSmartHeatingParameters().setSummerTemp(parseDouble);
        resetHVAC();
    }

    public void setWinterTemp(double parseDouble) {
        Model.getSimulationParameters().getSmartHeatingParameters().setWinterTemp(parseDouble);
        resetHVAC();
    }

    public void addRoomToZone(Room r, Zone currentZone) {
        Model.getHouse().addRoomToZone(r, currentZone);
        notifyObservers(this);
    }

    public void removeRoomFromZone(Room r, Zone currentZone) {
        Model.getHouse().removeRoomFromZone(r, currentZone);
        resetHVAC();
    }

    public void setMorningTempToZone(double parseDouble, Zone currentZone) {
        currentZone.setMorningTemp(parseDouble);
        resetHVAC();
    }

    public void setAfternoonTempToZone(double parseDouble, Zone currentZone) {
        currentZone.setAfternoonTemp(parseDouble);
        resetHVAC();
    }

    public void setNightTempToZone(double parseDouble, Zone currentZone) {
        currentZone.setNightTemp(parseDouble);
        resetHVAC();
    }

    public void setOverwriteTemp(double parseDouble, Zone currentZone) {
        currentZone.setOverwrittenTemp(parseDouble);
        resetHVAC();
    }

    public void removeOverwriteTemp(Zone currentZone) {
        currentZone.setOverwritten(false);
        resetHVAC();
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
                int currentMonth = Model.getSimulationParameters().getDateTime().getDate().get(Calendar.MONTH);
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
                }
            } else { // hvac is off, temperature goes toward outside
                double outsideTemp = Model.getSimulationParameters().getOutsideTemp();
                adjustToOutsideTemp(room, outsideTemp, currentTemp);

                if (room.getTemperature() <= desiredTemp - 0.24 || room.getTemperature() >= desiredTemp + 0.24)
                    room.setHvacState(true);
            }
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void adjustToOutsideTemp(Room room, double outsideTemp, double currentTemp) {
        outsideTemp = round(outsideTemp, 2);
        currentTemp = round(currentTemp, 2);

        if (currentTemp > outsideTemp) { // must decrease
            room.setTemperature(currentTemp - 0.05);
        } else if (currentTemp < outsideTemp){ // must increase
            room.setTemperature(currentTemp + 0.05);
        }
    }

    private void decreaseTempSummer(Room room, double currentTemp) {
        if (currentTemp >= Model.getSimulationParameters().getOutsideTemp()) {
            if (canOpenWindow(room)) {
                closeWindows(room);
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

    private float stringToFloatTemp(String temp) {
        String delimiter = "\\u00B0";
        String[] tokensVal = temp.split(delimiter);
        float tempFl = Float.parseFloat(tokensVal[0]);
        return tempFl;
    }


    private void adjustRoomTemp(double actualTemp, float desiredTemp, Room r) {
        String havcStatus = Model.getHouse().getHavcStatus();
        System.out.println(havcStatus);

        System.out.println("Actual Temp : " + actualTemp + " Desired Temp : " + desiredTemp);

        // if currently paused, check to see if need to resume
        if (havcStatus.equals("PAUSE") &&
                (actualTemp >= desiredTemp + 0.25 ||
                        actualTemp <= desiredTemp - 0.25)
        ) {
            System.out.println("1");
            Model.getHouse().setHavcStatus("RESUME");
        }

        havcStatus = Model.getHouse().getHavcStatus();

        // INITIALLY
        if (havcStatus.equals("START") &&
                (actualTemp >= desiredTemp + 1 ||
                        actualTemp <= desiredTemp - 1)
        ) {
            System.out.println("2");
            if (actualTemp < desiredTemp) actualTemp += 0.1;
            else if (actualTemp > desiredTemp) actualTemp -= 0.1;
            Model.getHouse().setHavcStatus("START_PASSED");
        }
        if (havcStatus.equals("START_PASSED") && (actualTemp != desiredTemp)) {
            System.out.println("3.0");
            if (actualTemp < desiredTemp) actualTemp += 0.1;
            else if (actualTemp > desiredTemp) actualTemp -= 0.1;
            if (actualTemp == desiredTemp) Model.getHouse().setHavcStatus("PAUSE");
        }
        if (havcStatus.equals("RESUME") &&
                (actualTemp >= desiredTemp + 0.25 ||
                        actualTemp <= desiredTemp - 0.25)
        ) {
            System.out.println("3");
            if (actualTemp < desiredTemp) actualTemp += 0.1;
            else if (actualTemp > desiredTemp) actualTemp -= 0.1;
            if (actualTemp == desiredTemp) Model.getHouse().setHavcStatus("PAUSE");
        }
        if (!havcStatus.equals("STOP") &&
                (actualTemp <= desiredTemp + 0.05 &&
                        actualTemp >= desiredTemp - 0.05))
//        if (!havcStatus.equals("STOP") &&
//                ( actualTemp == desiredTemp ))
        {
            System.out.println("4");
            Model.getHouse().setHavcStatus("PAUSE");
        }

        r.setTemperature(actualTemp);
        adjustRoomHavcSettings(r, desiredTemp);
    }

    public void adjustRoomHavcSettings(Room r, float desiredTemp) {
        Calendar currentDate = Model.getSimulationParameters().getDateTime().getDate();
        int currentMonth = currentDate.get(Calendar.MONTH);

        // if currently a summer month
        if (Model.getSimulationParameters().getSmartHeatingParameters().isSummer(currentMonth)) {
            adjustHavcForSummer(r, desiredTemp);
        }
        // if currently a winter month
        else {
            adjustHavcForWinter(r, desiredTemp);
        }
    }

    public void adjustHavcForSummer(Room r, float desiredTemp) {
        double actualTemp = r.getTemperature();
        String outsideTempStr = Double.toString(Model.getSimulationParameters().getOutsideTemp());
        float outsideTemp = stringToFloatTemp(outsideTempStr);
        boolean hasWindows = r.getWindows().size() > 0;
        boolean awayModeNotOn = Model.getSimulationParameters().isAwayModeOn() == false;

        // too cold in the room
        if (actualTemp < desiredTemp) {
            // colder outside
            if (actualTemp > outsideTemp) {
                r.getAC().setOn(false);
                r.getHeater().setOn(true);
            }
            // hotter outside
            else if (actualTemp < outsideTemp) {

                if (hasWindows && awayModeNotOn) {
                    openWindows(r);
                } else if (!hasWindows) {
                    r.getAC().setOn(false);
                    r.getHeater().setOn(true);
                }
            }
        }
        // too hot in the room
        else if (actualTemp > desiredTemp) {
            // colder outside
            if (actualTemp > outsideTemp) {
                if (hasWindows && awayModeNotOn) {
                    openWindows(r);
                } else if (!hasWindows) {
                    r.getAC().setOn(true);
                    r.getHeater().setOn(false);
                }
            }
            // hotter outside
            else if (actualTemp < outsideTemp) {
                r.getAC().setOn(true);
                r.getHeater().setOn(false);
            }
        }
    }

    public void adjustHavcForWinter(Room r, float desiredTemp) {
        double actualTemp = r.getTemperature();
        String outsideTempStr = Double.toString(Model.getSimulationParameters().getOutsideTemp());
        float outsideTemp = stringToFloatTemp(outsideTempStr);
        boolean hasWindows = r.getWindows().size() > 0;
        boolean awayModeNotOn = Model.getSimulationParameters().isAwayModeOn() == false;

        // too cold in the room
        if (actualTemp < desiredTemp) {
            // colder outside
            if (actualTemp > outsideTemp) {
                closeWindows(r);
                r.getAC().setOn(false);
                r.getHeater().setOn(true);
            }
            // hotter outside
            else if (actualTemp < outsideTemp) {
                r.getAC().setOn(false);
                r.getHeater().setOn(true);
            }
        }
        // too hot in the room
        else if (actualTemp > desiredTemp) {
            // colder outside
            if (actualTemp > outsideTemp) {
                if (hasWindows && awayModeNotOn) {
                    openWindows(r);
                } else if (!hasWindows) {
                    r.getAC().setOn(true);
                    r.getHeater().setOn(false);
                }
            }
            // hotter outside
            else if (actualTemp < outsideTemp) {
                closeWindows(r);
                r.getAC().setOn(true);
                r.getHeater().setOn(false);
            }
        } else {
            r.getAC().setOn(false);
            r.getHeater().setOn(false);
        }
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


    public void notifiesTimeUpdateEnd() {
        ArrayList<Room> rooms = Model.getHouse().getRooms();
        String outsideTempStr = Double.toString(Model.getSimulationParameters().getOutsideTemp());
        float outsideTemp = stringToFloatTemp(outsideTempStr);

        for (Room r : rooms) {
            System.out.println(r.getName());
            adjustRoomTempToOutside(r.getTemperature(), outsideTemp, r);
        }
        notifyObservers(this);

    }

    public void adjustRoomTempToOutside(double actualTemp, float outsideTemp, Room r) {
        System.out.println("Actual Temp : " + actualTemp + " Desired Temp : " + outsideTemp);

        if ((actualTemp <= outsideTemp + 0.1 ||
                actualTemp >= outsideTemp - 0.1)) {
            if (actualTemp < outsideTemp) actualTemp += 0.05;
            else if (actualTemp > outsideTemp) actualTemp -= 0.05;
        }

        r.setTemperature(actualTemp);
        adjustRoomHavcSettings(r, outsideTemp);
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
