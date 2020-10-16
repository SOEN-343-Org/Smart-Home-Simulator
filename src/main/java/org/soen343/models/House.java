package org.soen343.models;

import java.util.ArrayList;
import java.util.HashMap;

public class House {

    // Used for drawing the house layout
    private final Room[][] layout;

    // Doors and windows have the same reference to the objects inside layout
    private final HashMap<Integer, Room> rooms;

    public ArrayList<String> roomsName = new ArrayList<>();

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
     * @return Array of rooms
     */
    public ArrayList<Room> getRooms() {
        return new ArrayList<>(rooms.values());
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

    public Individual getIndividualByName(String name) {
        for (Individual ind : individuals.values()) {
            if (ind.getName().equals(name)) {
                return ind;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "House of dimension (" + layout.length + " by " + layout[0].length + ")\nRooms=\n" + rooms;
    }

}
