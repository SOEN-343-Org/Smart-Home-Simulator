package org.soen343.models.house;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {

    private final HashMap<Integer, Window> windows;
    private final HashMap<Integer, Door> doors;
    private final HashMap<Integer, Light> lights;
    private final int id;
    private final String name;
    private final Heater heater;
    private final AC ac;
    private final Object top;
    private final Object right;
    private final Object down;
    private final Object left;
    private double temperature;

    /**
     * @param id     The room's id
     * @param name   The room's name
     * @param lights The list of Light object in the room
     * @param top    The object (Window or Door) that is on the north wall
     * @param right  The object (Window or Door) that is on the east wall
     * @param down   The object (Window or Door) that is on the south wall
     * @param left   The object (Window or Door) that is on the west wall
     */
    public Room(int id, String name, Light[] lights, Object top, Object right, Object down, Object left) {
        this.name = name;
        this.id = id;

        this.lights = new HashMap<>();
        this.windows = new HashMap<>();
        this.doors = new HashMap<>();
        this.top = top;
        this.right = right;
        this.down = down;
        this.left = left;

        temperature = 20;

        this.heater = new Heater();
        this.ac = new AC();

        for (Light light : lights) {
            insertInsideHashMap(light);
        }
        insertInsideHashMap(this.top);
        insertInsideHashMap(this.right);
        insertInsideHashMap(this.down);
        insertInsideHashMap(this.left);
    }

    /**
     * Helper method to place objects into their good hashmap
     *
     * @param object Door, Window, Light object
     */
    private void insertInsideHashMap(Object object) {
        if (object == null) {
            return;
        }
        if (object instanceof Door) {
            doors.put(((Door) object).getId(), (Door) object);
        } else if (object instanceof Window) {
            windows.put(((Window) object).getId(), (Window) object);
        } else {
            lights.put(((Light) object).getId(), (Light) object);
        }
    }

    /**
     * Gets a window by id
     *
     * @param id window's id
     * @return Window object
     */
    public Window getWindow(int id) {
        return windows.get(id);
    }

    /**
     * Gets a door by id
     *
     * @param id door's id
     * @return Door object
     */
    public Door getDoor(int id) {
        return doors.get(id);
    }

    /**
     * Gets a light by id
     *
     * @param id light's id
     * @return Light object
     */
    public Light getLight(int id) {
        return lights.get(id);
    }

    /**
     * Gets all windows in the room
     *
     * @return List of Window objects
     */
    public ArrayList<Window> getWindows() {
        return new ArrayList<>(windows.values());
    }

    /**
     * Gets all the doors in the room
     *
     * @return List of Door objects
     */
    public ArrayList<Door> getDoors() {
        return new ArrayList<>(doors.values());
    }

    /**
     * Get all the lights in the room
     *
     * @return List of Light objects
     */
    public ArrayList<Light> getLights() {
        return new ArrayList<>(lights.values());
    }

    /**
     * Gets the name of the room
     *
     * @return String of the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the id of the room
     *
     * @return Int id
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }

    //TODO: Could create a superclass for Window and Door

    /**
     * Gets the object in the north wall
     *
     * @return Object
     */
    public Object getTop() {
        return top;
    }

    /**
     * Gets the object in the east wall
     *
     * @return Object
     */
    public Object getRight() {
        return right;
    }

    /**
     * Gets the object in the south wall
     *
     * @return Object
     */
    public Object getDown() {
        return down;
    }

    /**
     * Gets the object in the west wall
     *
     * @return Object
     */
    public Object getLeft() {
        return left;
    }

    /**
     * Returns the heater
     *
     * @return Heater
     */
    public Heater getHeater() {
        return heater;
    }

    /**
     * Returns the AC
     *
     * @return AC
     */
    public AC getAC() {
        return ac;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
