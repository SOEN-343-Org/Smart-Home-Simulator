package org.soen343.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.soen343.models.*;

public class HouseLayoutController {

    private final double safeZoneH = 35;
    private double safeZoneW;
    private double roomSize;
    private double width;
    private double height;

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private Room[][] layout;
    private Image grass;
    private Image floor;
    private Image openedDoor;
    private Image closedDoor;
    private Image openedWindow;
    private Image closedWindow;
    private Image blocker;
    private Image openedLight;
    private Image closedLight;


    /**
     * Initialize the data in the scene, is called when fxml is loaded and displayed
     */
    @FXML
    public void initialize() {
        // Initialization code can go here.

        grass = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/grass.jpg")));
        floor = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/floor.jpg")));
        openedDoor = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/opened_door.png")));
        closedDoor = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/closed_door.png")));
        openedWindow = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/opened_window.png")));
        closedWindow = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/closed_window.png")));
        blocker = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/blocker.png")));
        openedLight = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/opened_light.png")));
        closedLight = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/closed_light.png")));
    }

    /**
     * Creates the canvas with the inputted width and height
     *
     * @param w width
     * @param h height
     */
    public void createCanvas(double w, double h) {
        width = w;
        height = h;
        canvas.setWidth(w);
        canvas.setHeight(h);
        gc = canvas.getGraphicsContext2D();
    }

    /**
     * Setter to set the house layout
     *
     * @param house House object
     */
    public void setHouseLayout(House house) {
        if (house == null) {
            drawNullLayout();
            return;
        }
        layout = house.getLayout();
        roomSize = (int) Math.round((canvas.getHeight() - (2 * safeZoneH)) / layout.length);
        safeZoneW = (int) Math.round((canvas.getWidth() - (roomSize * layout[0].length)) / 2);
        drawLayout();
    }

    /**
     * Draw a a text indicating that the house is null and to restart the application
     */
    private void drawNullLayout() {
        gc.setLineWidth(8.0);
        gc.strokeRect(0, 0, width, height);
        gc.setStroke(Color.BLACK);
        gc.setFont(new Font(20));
        Text text = new Text("HOUSE LAYOUT IS NULL, RESTART THE APPLICATION");
        text.setFont(new Font(20));
        double textSize = text.getBoundsInLocal().getWidth();
        gc.strokeText(text.getText(), (width - textSize) / 2, height / 2.0);
    }


    /**
     * @param object Door or Window object
     * @return True if the object is a Door
     */
    private boolean isDoor(Object object) {
        // Not the best and prettiest way but there is no place to put this yet.
        return object instanceof Door;
    }

    /**
     * Draw the house layout in the canvas
     */
    private void drawLayout() {

        gc.drawImage(grass, 0, 0, width, height);
        gc.setLineWidth(8.0);

        gc.strokeRect(0, 0, width, height);

        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] != null) {
                    gc.drawImage(floor, safeZoneW + roomSize * j, safeZoneH + roomSize * i, roomSize, roomSize);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(10.0);
                    gc.strokeRect(safeZoneW + roomSize * j, safeZoneH + roomSize * i, roomSize, roomSize);
                }
            }
        }

        // Loop through all the rooms in the layout 2d array
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                Room room = layout[i][j];
                if (room != null) {
                    // Draw floor, walls and name of room
                    double fontSize = 20;
                    gc.setFont(new Font(fontSize));
                    gc.setFill(Color.ALICEBLUE);
                    Text text = new Text(room.getName());
                    text.setFont(new Font(fontSize));
                    double textSize = text.getBoundsInLocal().getWidth();
                    gc.fillText(room.getName(), (safeZoneW + roomSize * j) + (roomSize - textSize) / 2, (safeZoneH + roomSize * i) + (roomSize / 2));

                    // Time to draw the icons

                    // Doors and windows and blockers
                    gc.setFill(Color.ALICEBLUE);

                    double iconSize = 35;
                    if (room.getTop() != null && isDoor(room.getTop())) {
                        gc.drawImage((((Door) room.getTop()).isOpen() ? openedDoor : closedDoor), (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0, (safeZoneH + roomSize * i) - (iconSize / 2.0), iconSize, iconSize);
                        gc.fillText(Integer.toString(((Door) room.getTop()).getId()), (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0 + iconSize / 2.0, (safeZoneH + roomSize * i) + iconSize / 4.0);
                    } else if (room.getTop() != null && !isDoor(room.getTop())) {
                        gc.drawImage((((Window) room.getTop()).isOpen() ? openedWindow : closedWindow), (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0, (safeZoneH + roomSize * i) - (iconSize / 2.0), iconSize, iconSize);
                        gc.fillText(Integer.toString(((Window) room.getTop()).getId()), (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0 + iconSize / 3.0, (safeZoneH + roomSize * i) + iconSize / 4.0);
                        if (((Window) room.getTop()).isBlocked()) {
                            gc.drawImage(blocker, (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0, (safeZoneH + roomSize * i) + iconSize / 2.0, iconSize, iconSize);
                        }
                    }
                    if (room.getRight() != null && isDoor(room.getRight())) {
                        gc.drawImage((((Door) room.getRight()).isOpen() ? openedDoor : closedDoor), (safeZoneW + roomSize * j) + (roomSize - iconSize / 2.0), (safeZoneH + roomSize * i) + (roomSize - iconSize) / 2.0, iconSize, iconSize);
                        gc.fillText(Integer.toString(((Door) room.getRight()).getId()), (safeZoneW + roomSize * j) + roomSize, (safeZoneH + roomSize * i) + (roomSize / 2.0 + iconSize / 4.0));
                    } else if (room.getRight() != null && !isDoor(room.getRight())) {
                        gc.drawImage((((Window) room.getRight()).isOpen() ? openedWindow : closedWindow), (safeZoneW + roomSize * j) + (roomSize - iconSize / 2.0), (safeZoneH + roomSize * i) + (roomSize - iconSize) / 2.0, iconSize, iconSize);
                        gc.fillText(Integer.toString(((Window) room.getRight()).getId()), (safeZoneW + roomSize * j) + (roomSize - iconSize / 8.0), (safeZoneH + roomSize * i) + (roomSize / 2.0 + iconSize / 4.0));
                        if (((Window) room.getRight()).isBlocked()) {
                            gc.drawImage(blocker, (safeZoneW + roomSize * j) + (roomSize - iconSize / 2.0) - iconSize, (safeZoneH + roomSize * i) + (roomSize - iconSize) / 2.0, iconSize, iconSize);
                        }
                    }
                    if (room.getDown() != null && isDoor(room.getDown())) {
                        gc.drawImage((((Door) room.getDown()).isOpen() ? openedDoor : closedDoor), (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0, (safeZoneH + roomSize * i) + (roomSize - iconSize / 2.0), iconSize, iconSize);
                        gc.fillText(Integer.toString(((Door) room.getDown()).getId()), (safeZoneW + roomSize * j) + (roomSize / 2.0), (safeZoneH + roomSize * i) + roomSize + iconSize / 4.0);
                    } else if (room.getDown() != null && !isDoor(room.getDown())) {
                        gc.drawImage((((Window) room.getDown()).isOpen() ? openedWindow : closedWindow), (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0, (safeZoneH + roomSize * i) + (roomSize - iconSize / 2.0), iconSize, iconSize);
                        gc.fillText(Integer.toString(((Window) room.getDown()).getId()), (safeZoneW + roomSize * j) + (roomSize - iconSize / 3.0) / 2.0, (safeZoneH + roomSize * i) + roomSize + iconSize / 4.0);
                        if (((Window) room.getDown()).isBlocked()) {
                            gc.drawImage(blocker, (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0, (safeZoneH + roomSize * i) + (roomSize - iconSize / 2.0) - iconSize, iconSize, iconSize);
                        }
                    }
                    if (room.getLeft() != null && isDoor(room.getLeft())) {
                        gc.drawImage((((Door) room.getLeft()).isOpen() ? openedDoor : closedDoor), (safeZoneW + roomSize * j) - (iconSize / 2.0), (safeZoneH + roomSize * i) + (roomSize - iconSize) / 2.0, iconSize, iconSize);
                        gc.fillText(Integer.toString(((Door) room.getLeft()).getId()), (safeZoneW + roomSize * j), (safeZoneH + roomSize * i) + (roomSize / 2.0 + iconSize / 4.0));
                    } else if (room.getLeft() != null && !isDoor(room.getLeft())) {
                        gc.drawImage((((Window) room.getLeft()).isOpen() ? openedWindow : closedWindow), (safeZoneW + roomSize * j) - (iconSize / 2.0), (safeZoneH + roomSize * i) + (roomSize - iconSize) / 2.0, iconSize, iconSize);
                        gc.fillText(Integer.toString(((Window) room.getLeft()).getId()), (safeZoneW + roomSize * j) - iconSize / 9.0, (safeZoneH + roomSize * i) + (roomSize / 2.0 + iconSize / 4.0));
                        if (((Window) room.getLeft()).isBlocked()) {
                            gc.drawImage(blocker, (safeZoneW + roomSize * j) - (iconSize / 2.0) + iconSize, (safeZoneH + roomSize * i) + (roomSize - iconSize) / 2.0, iconSize, iconSize);
                        }
                    }

                    // Lights - in corner
                    // There is a maximum of 4 items (lights, heater, AC) that will be placed in the corners
                    int currentObjectPosition = 0;
                    Light[] lights = room.getLights();
                    for (Light light : lights) {
                        if (currentObjectPosition == 0) {
                            currentObjectPosition++;
                            gc.drawImage((light.isOpen() ? openedLight : closedLight), (safeZoneW + roomSize * j), (safeZoneH + roomSize * i), iconSize, iconSize);
                            gc.setFill(light.isOpen() ? Color.BLACK : Color.ALICEBLUE);
                            gc.fillText(Integer.toString(light.getId()), (safeZoneW + roomSize * j) + iconSize / 3.0, (safeZoneH + roomSize * i) + iconSize * 0.7);
                        } else if (currentObjectPosition == 1) {
                            currentObjectPosition++;
                            gc.drawImage((light.isOpen() ? openedLight : closedLight), (safeZoneW + roomSize * j) + roomSize - iconSize, (safeZoneH + roomSize * i), iconSize, iconSize);
                            gc.setFill(light.isOpen() ? Color.BLACK : Color.ALICEBLUE);
                            gc.fillText(Integer.toString(light.getId()), (safeZoneW + roomSize * j) + roomSize - iconSize * 0.7, (safeZoneH + roomSize * i) + iconSize * 0.7);
                        } else if (currentObjectPosition == 2) {
                            currentObjectPosition++;
                            gc.drawImage((light.isOpen() ? openedLight : closedLight), (safeZoneW + roomSize * j), (safeZoneH + roomSize * i) + roomSize - iconSize, iconSize, iconSize);
                            gc.setFill(light.isOpen() ? Color.BLACK : Color.ALICEBLUE);
                            gc.fillText(Integer.toString(light.getId()), (safeZoneW + roomSize * j) + iconSize / 3.0, (safeZoneH + roomSize * i) + roomSize - iconSize / 3.0);
                        } else if (currentObjectPosition == 3) {
                            currentObjectPosition++;
                            gc.drawImage((light.isOpen() ? openedLight : closedLight), (safeZoneW + roomSize * j) + roomSize - iconSize, (safeZoneH + roomSize * i) + roomSize - iconSize, iconSize, iconSize);
                            gc.setFill(light.isOpen() ? Color.BLACK : Color.ALICEBLUE);
                            gc.fillText(Integer.toString(light.getId()), (safeZoneW + roomSize * j) + roomSize - iconSize * 0.7, (safeZoneH + roomSize * i) + roomSize - iconSize / 3.0);
                        }
                    }

                    // User - under text
                    //TODO: Draw users in the house layout
                }
            }
        }
    }
}
