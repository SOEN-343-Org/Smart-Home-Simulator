package org.soen343.models.house;

public class Heater implements Components {
    private boolean on;

    public Heater() {
        this.on = true;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    @Override
    public String getName() {
        return "Heater is " + on;
    }
}
