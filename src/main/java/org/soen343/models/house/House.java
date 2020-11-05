package org.soen343.models.house;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type House.
 */
public class House {

    // Used for drawing the house layout
    private final Room[][] layout;

    // Doors and windows have the same reference to the objects inside layout
    private final HashMap<Integer, Room> rooms;

    /**
     * The Rooms name.
     */
    public ArrayList<String> roomsName = new ArrayList<>();

    /**
     * The Individuals.
     */
    public HashMap<Integer, Individual> individuals = new HashMap<>();

    /**
     * Constructor for a House, should only be called once
     *
     * @param layout 2d array of Rooms
     */
    public House(Room[][] layout) {
        this.layout = layout;
        this.rooms = new HashMap<>();
        for (Room[] value : layout) {
            for (Room room : value) {
                if (room != null) {
                    rooms.put(room.getId(), room);
                    roomsName.add(room.getName());
                }
            }
        }
        roomsName.add("outside");
    }

    /**
     * Gets the layout of the house
     *
     * @return 2d array of rooms (layout)
     */
    public Room[][] getLayout() {
        return layout;
    }

    /**
     * Gets all the rooms in the house
     *
     * @return ArrayList of rooms
     */
    public ArrayList<Room> getRooms() {
        return new ArrayList<>(rooms.values());
    }

    /**
     * Gets all the individuals in the house
     *
     * @return ArrayList of Individual
     */
    public ArrayList<Individual> getIndividuals() {
        return new ArrayList<>(individuals.values());
    }

    /**
     * Get a room by its id
     *
     * @param id int id of the room
     * @return Room object
     */
    public Room getRoomById(int id) {
        return rooms.get(id);
    }


    /**
     * Gets door by id.
     *
     * @param id the id
     * @return the door by id
     */
    public Door getDoorById(int id) {
        ArrayList<Room> rooms = getRooms();
        for (Room room : rooms) {
            Door door = room.getDoor(id);
            if (door != null) {
                return door;
            }
        }
        return null;
    }

    /**
     * Gets window by id.
     *
     * @param id the id
     * @return the window by id
     */
    public Window getWindowById(int id) {
        ArrayList<Room> rooms = getRooms();
        for (Room room : rooms) {
            Window window = room.getWindow(id);
            if (window != null) {
                return window;
            }
        }
        return null;
    }

    /**
     * Gets light by id.
     *
     * @param id the id
     * @return the light by id
     */
    public Light getLightById(int id) {
        ArrayList<Room> rooms = getRooms();
        for (Room room : rooms) {
            Light light = room.getLight(id);
            if (light != null) {
                return light;
            }
        }
        return null;
    }

    public ArrayList<Light> getAllLights() {
        ArrayList<Room> rooms = getRooms();
        ArrayList<Light> lights = new ArrayList<>();
        for (Room room : rooms) {
            lights.addAll(room.getLights());
        }
        return lights;
    }

    @Override
    public String toString() {
        return "House of dimension (" + layout.length + " by " + layout[0].length + ")\nRooms=\n" + rooms;
    }

    /**
     * Gets room by name.
     *
     * @param location the location
     * @return the room by name
     */
    public Room getRoomByName(String location) {
        ArrayList<Room> rooms = getRooms();
        for (Room room : rooms) {
            if (room.getName().equals(location)) {
                return room;
            }
        }
        return null;
    }

    public ArrayList<Window> getAllWindows() {
        ArrayList<Room> rooms = getRooms();
        ArrayList<Window> windows = new ArrayList<>();
        for (Room room : rooms) {
            windows.addAll(room.getWindows());
        }
        return windows;
    }

    public ArrayList<Door> getAllDoors() {
        ArrayList<Room> rooms = getRooms();
        ArrayList<Door> doors = new ArrayList<>();
        for (Room room : rooms) {
            doors.addAll(room.getDoors());
        }
        return doors;
    }

    /**
     * Gets room by door id.
     *
     * @param id the id
     * @return the room by door id
     */
    public ArrayList<Room> getRoomByDoorId(int id) {
        ArrayList<Room> roomsWithDoor = new ArrayList<>();
        ArrayList<Room> rooms = getRooms();

        for (Room room : rooms) {
            if (room.getDoor(id) != null) {
                roomsWithDoor.add(room);
            }
        }
        if (roomsWithDoor.size() != 0) {
            return roomsWithDoor;
        }
        return null;
    }

    /**
     * Gets room by light id.
     *
     * @param id the id
     * @return the room by light id
     */
    public Room getRoomByLightId(int id) {
        Room roomWithLight = null;
        ArrayList<Room> rooms = getRooms();

        for (Room room : rooms) {
            if (room.getLight(id) != null) {
                roomWithLight = room;
            }
        }
        if (roomWithLight != null) {
            return roomWithLight;
        }
        return null;
    }

    /**
     * Gets room by window id.
     *
     * @param id the id
     * @return the room by window id
     */
    public Room getRoomByWindowId(int id) {
        Room roomWithWindow = null;
        ArrayList<Room> rooms = getRooms();

        for (Room room : rooms) {
            if (room.getWindow(id) != null) {
                roomWithWindow = room;
            }
        }
        if (roomWithWindow != null) {
            return roomWithWindow;
        }
        return null;
    }
}
