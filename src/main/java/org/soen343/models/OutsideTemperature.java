package org.soen343.models;

/**
 * The type Outside temperature.
 */
public class OutsideTemperature {
    private Integer temperature;

    /**
     * Instantiates a new Outside temperature.
     */
    public OutsideTemperature() {
        this.temperature = 0;
    }

    /**
     * Sets the outside temperature.
     *
     * @param temp Integer temperature outside.
     */
    public void setTemperature(Integer temp) {
        this.temperature = temp;
    }

    /**
     * Gets outside temperature.
     *
     * @return the temperature
     */
    public Integer getTemperature() { return temperature; }
}
