package org.soen343.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.soen343.exceptions.IncorrectFileNameException;
import org.soen343.exceptions.InvalidOptionException;
import org.soen343.models.house.*;

import java.io.InputStream;

/**
 * The type House layout util.
 */
public class HouseLayoutUtil {

    /**
     * Read the house layout and creates an House object
     *
     * @return House object
     */
    public static House ReadHouseLayoutFile() throws IncorrectFileNameException, InvalidOptionException {

        String fileName = "house-layout.json";
        String fileUrl = "/org/soen343/houseLayout/" + fileName;

        if (!fileName.equals("house-layout.json")) throw new IncorrectFileNameException("Incorrect File Name : " + fileName);

        InputStream inputStream = HouseLayoutUtil.class.getResourceAsStream(fileUrl);
        if (inputStream == null) {
            throw new NullPointerException("Cannot find the house-layout file at " + fileUrl);
        }

        try {
            JSONTokener token = new JSONTokener(inputStream);
            JSONObject object = new JSONObject(token);

            // Retrieve the dimensions of the house
            JSONObject dimensions = object.getJSONObject("dimensions");
            int rows = dimensions.getInt("rows");
            int columns = dimensions.getInt("columns");

            // Create the 2d array of null rooms
            Room[][] house = new Room[rows][columns];

            // Retrieve the list of rooms from the file
            JSONArray rooms = object.getJSONArray("rooms");

            int roomId = 1;
            int doorId = 1;
            int windowId = 1;
            int lightId = 1;
            for (int i = 0; i < rooms.length(); i++) {
                JSONObject roomJson = rooms.getJSONObject(i);

                String name = roomJson.getString("name");

                int numberOfLights = roomJson.getInt("lights");
                Light[] lights = new Light[numberOfLights];
                for (int j = 0; j < numberOfLights; j++) {
                    lights[j] = new Light(lightId++);
                }

                JSONObject location = roomJson.getJSONObject("location");
                int row = location.getInt("row");
                int column = location.getInt("column");

                // top
                Object top = roomJson.get("top");
                Object topObject = null;
                if (top != JSONObject.NULL) {
                    if (top.toString().equalsIgnoreCase("window")) {
                        topObject = new Window(windowId++);
                    } else if (top.toString().equalsIgnoreCase("door")) {
                        // check if top room is initialized and take that door object if it is, else create a new one
                        if (row > 0 && house[row - 1][column] != null) {
                            topObject = house[row - 1][column].getDown();
                        } else {
                            topObject = new Door(doorId++);
                        }
                    } else {
                        throw new InvalidOptionException(top);
                    }
                }

                // right
                Object right = roomJson.get("right");
                Object rightObject = null;
                if (right != JSONObject.NULL) {
                    if (right.toString().equalsIgnoreCase("window")) {
                        rightObject = new Window(windowId++);
                    } else if (right.toString().equalsIgnoreCase("door")) {
                        // check if right room is initialized and take that door object if it is, else create a new one
                        if (column < columns - 1 && house[row][column + 1] != null) {
                            rightObject = house[row][column + 1].getLeft();
                        } else {
                            rightObject = new Door(doorId++);
                        }
                    } else {
                        throw new InvalidOptionException(top);
                    }
                }

                // down
                Object down = roomJson.get("down");
                Object downObject = null;
                if (down != JSONObject.NULL) {
                    if (down.toString().equalsIgnoreCase("window")) {
                        downObject = new Window(windowId++);
                    } else if (down.toString().equalsIgnoreCase("door")) {
                        // check if down room is initialized and take that door object if it is, else create a new one
                        if (row < rows - 1 && house[row + 1][column] != null) {
                            downObject = house[row + 1][column].getTop();
                        } else {
                            downObject = new Door(doorId++);
                        }
                    } else {
                        throw new InvalidOptionException(top);
                    }
                }

                // left
                Object left = roomJson.get("left");
                Object leftObject = null;
                if (left != JSONObject.NULL) {
                    if (left.toString().equalsIgnoreCase("window")) {
                        leftObject = new Window(windowId++);
                    } else if (left.toString().equalsIgnoreCase("door")) {
                        // check if left room is initialized and take that door object if it is, else create a new one
                        if (column > 0 && house[row][column - 1] != null) {
                            leftObject = house[row][column - 1].getRight();
                        } else {
                            leftObject = new Door(doorId++);
                        }
                    } else {
                        throw new InvalidOptionException(top);
                    }
                }
                Room r = new Room(roomId++, name, lights, topObject, rightObject, downObject, leftObject);
                house[row][column] = r;
            }

            return new House(house);
        } catch (Exception e) {
            System.out.println("Error reading the house layout file.");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
