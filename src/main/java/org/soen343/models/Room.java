package org.soen343.models;

import java.util.HashMap;

public class Room implements Observer{

    private final HashMap<Integer, Window> windows;
    private final HashMap<Integer, Door> doors;
    private final HashMap<Integer, Light> lights;
//    private final HashMap<Integer, Individual> individuals;
    private final int id;
    public HashMap<Integer, Individual> individuals;
    private final String name;
    // shared amongst all class instances to act as a counter
    private static int observerIDTracker= 0;

    // For each individual Observer object created
    private int observerID;

    // ref for Reporter
    private Reporter individual;

    private final Object top;
    private final Object right;
    private final Object down;
    private final Object left;

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
        this.individuals = new HashMap<>();
        this.top = top;
        this.right = right;
        this.down = down;
        this.left = left;

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
     * Add an individual in a room
     *
     * @param ind Individual to add in a room
     * @return True if the individual is added
     */
    public boolean addIndividual(Individual ind) {
        try {
            individuals.put(ind.getId(), ind);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Remove an individual from a room
     *
     * @param id id of the individual to remove
     * @return True if the individual is removed
     */
    public boolean removeIndividual(int id) {
        try {
            individuals.remove(id);
        } catch (Exception e) {
            return false;
        }
        return true;
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
     * Gets an individual by id
     *
     * @param id individual's id
     * @return Individual object
     */
    public Individual getIndividual(int id) {
        return individuals.get(id);
    }

    /**
     * Gets all windows in the room
     *
     * @return List of Window objects
     */
    public Window[] getWindows() {
        Window[] windowsArray = new Window[lights.size()];
        Object[] objectsArray = windows.values().toArray();
        for (int i = 0; i < windowsArray.length; i++) {
            windowsArray[i] = (Window) objectsArray[i];
        }
        return windowsArray;
    }

    /**
     * Gets all the doors in the room
     *
     * @return List of Door objects
     */
    public Door[] getDoors() {
        Door[] doorsArray = new Door[doors.size()];
        Object[] objectsArray = doors.values().toArray();
        for (int i = 0; i < doorsArray.length; i++) {
            doorsArray[i] = (Door) objectsArray[i];
        }
        return doorsArray;
    }

    /**
     * Get all the lights in the room
     *
     * @return List of Light objects
     */
    public Light[] getLights() {
        Light[] lightsArray = new Light[lights.size()];
        Object[] objectsArray = lights.values().toArray();
        for (int i = 0; i < lightsArray.length; i++) {
            lightsArray[i] = (Light) objectsArray[i];
        }
        return lightsArray;
    }

    /**
     * Get all the individuals in the room
     *
     * @return List of Individual objects
     */
    public Individual[] getIndividuals() {
        Individual[] individualsArray = new Individual[individuals.size()];
        Object[] objectsArray = individuals.values().toArray();
        for (int i = 0; i < individualsArray.length; i++) {
            individualsArray[i] = (Individual) objectsArray[i];
        }
        return individualsArray;
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
        return "name=" + name + ", windows=" + windows + ", doors=" + doors + ", lights=" + lights + "\n";
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
     * This method adds or removes individuals
     * from its list of individuals.
     * @param o Object is an Individual that the location
     *          is observing.
     */
    @Override
    public void update(Object o) {
        Individual ind = (Individual) o;
         boolean correctLocation = ind.location.equals(this.name);
         boolean individualIsSubscribed;
         if (individuals.get(ind.id) == null) {
             individualIsSubscribed = false;
         } else {
             individualIsSubscribed = true;
         }
        // is the object.location equal to this.name of room ?
            // if so then is the object.id present in the individuals hashmap ?
                // if so then do nothing
            // else
                // add this object individual to your individuals list
        // else if object.location does not equal that of this room
            // if so then is the object.id present in the individuals hashmap ?
                //if so then remove individual from the individuals hashmap

        if (correctLocation && !individualIsSubscribed) {
            individuals.put(ind.id, ind);
        } else if (!correctLocation && individualIsSubscribed) {
            individuals.remove(ind.id);
        }

        System.out.println("Room : " + this.name + " individuals : " + individuals);
    }
}
