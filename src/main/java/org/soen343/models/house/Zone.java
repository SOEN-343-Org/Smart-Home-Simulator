package org.soen343.models.house;

import java.util.ArrayList;

public class Zone {

    private final String name;
    private ArrayList<Room> rooms;
    private double nightTemp;
    private double morningTemp;
    private double afternoonTemp;

    private boolean overwritten;
    private double overwrittenTemp;

    public Zone(String name) {
        this.name = name;
        rooms = new ArrayList<>();
        nightTemp = 20;
        morningTemp = 20;
        afternoonTemp = 20;
        overwritten = false;
        overwrittenTemp = 0;
    }


    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void removeRoom(Room room) {
        this.rooms.remove(room);
    }

    public double getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(double nightTemp) {
        this.nightTemp = nightTemp;
    }

    public double getMorningTemp() {
        return morningTemp;
    }

    public void setMorningTemp(double morningTemp) {
        this.morningTemp = morningTemp;
    }

    public double getAfternoonTemp() {
        return afternoonTemp;
    }

    public void setAfternoonTemp(double afternoonTemp) {
        this.afternoonTemp = afternoonTemp;
    }

    public double getOverwrittenTemp() {
        return overwrittenTemp;
    }

    public void setOverwrittenTemp(double overwrittenTemp) {
        this.overwrittenTemp = overwrittenTemp;
        overwritten = true;
    }

    public boolean getOverwritten() {
        return overwritten;
    }

    public void setOverwritten(boolean overwritten) {
        this.overwritten = overwritten;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
