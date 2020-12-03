package org.soen343.services.modules;

import org.soen343.models.Model;
import org.soen343.models.house.Room;
import org.soen343.models.house.Window;
import org.soen343.models.house.Zone;
import org.soen343.services.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

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

        ArrayList<Zone> zones = Model.getHouse().getZones();

        for (Zone z : zones) {
            String desiredTempStr = shhModule.getDesiredTempFromZone(z);
            float desiredTemp = stringToFloatTemp(desiredTempStr);

            ArrayList<Room> zoneRooms = z.getRooms();
            for (Room r : zoneRooms) {

                System.out.println(r.getName());
                adjustRoomTemp(r.getTemperature(), desiredTemp, r);
            }
        }
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
                ( actualTemp >= desiredTemp+0.25 ||
                actualTemp <= desiredTemp-0.25 )
        ) {
            System.out.println("1");
            Model.getHouse().setHavcStatus("RESUME");
        }

        havcStatus = Model.getHouse().getHavcStatus();

        // INITIALLY
        if (havcStatus.equals("START") &&
                ( actualTemp >= desiredTemp+1 ||
                actualTemp <= desiredTemp-1 )
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
                ( actualTemp >= desiredTemp+0.25 ||
                actualTemp <= desiredTemp-0.25 )
        ) {
            System.out.println("3");
            if (actualTemp < desiredTemp) actualTemp += 0.1;
            else if (actualTemp > desiredTemp) actualTemp -= 0.1;
            if (actualTemp == desiredTemp) Model.getHouse().setHavcStatus("PAUSE");
        }
        if (!havcStatus.equals("STOP") &&
                ( actualTemp <= desiredTemp+0.05 &&
                actualTemp >= desiredTemp-0.05 ))
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

    public void adjustHavcForSummer(Room r, float desiredTemp){
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

    public void adjustHavcForWinter(Room r, float desiredTemp){
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
        }
        else {
            r.getAC().setOn(false);
            r.getHeater().setOn(false);
        }
    }

    public void openWindows(Room r) {
        ArrayList<Window> windows = r.getWindows();

        for (Window w : windows) {
            if (!w.isBlocked()) {
                w.setOpen(true);
            } else {
                // TODO : notify user
            }
        }
    }

    public void closeWindows(Room r) {
        ArrayList<Window> windows = r.getWindows();

        for (Window w : windows) {
            if (!w.isBlocked()) {
                w.setOpen(false);
            } else {
                // TODO : notify user
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

        if ( ( actualTemp <= outsideTemp+0.1 ||
                actualTemp >= outsideTemp-0.1 ) )
        {
            if (actualTemp < outsideTemp) actualTemp += 0.05;
            else if (actualTemp > outsideTemp) actualTemp -= 0.05;
        }

        r.setTemperature(actualTemp);
        adjustRoomHavcSettings(r, outsideTemp);
    }

}
