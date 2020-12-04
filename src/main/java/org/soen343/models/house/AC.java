package org.soen343.models.house;

public class AC implements Components{
    private boolean on;

    public AC() {
        this.on = false;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    @Override
    public String getName() {
        return "AC is " + on;
    }
}
