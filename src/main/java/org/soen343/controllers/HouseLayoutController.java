package org.soen343.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.soen343.drawing.*;
import org.soen343.models.Model;
import org.soen343.models.house.*;

import java.util.ArrayList;


public class HouseLayoutController extends Controller {

    private final double SAFE_ZONE_H = 35;
    private double safeZoneW;
    private double roomSize;
    private boolean nullHouse = false;

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private Room[][] layout;
    private Image grass;

    @FXML
    public void initialize() {
        // Initialization code can go here.
        Corner.intitializeImages();
        DoorsAndBlockers.initalizeImages();
        Images.intitializeImages();
        Individuals.intitializeImages();
        grass = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/grass.jpg")));
        gc = canvas.getGraphicsContext2D();
    }

    /**
     * Initialize house layout controller
     */
    public void initializeController() {
        layout = Model.getHouse().getLayout();

        if (layout == null) {
            nullHouse = true;
            return;
        }
        roomSize = (int) Math.round((canvas.getHeight() - (2 * SAFE_ZONE_H)) / layout.length);
        safeZoneW = (int) Math.round((canvas.getWidth() - (roomSize * layout[0].length)) / 2);
    }

    @Override
    void update() {
        drawLayout();
    }


    private void drawAllRooms(){

        // Loop through all the rooms in the layout 2d array
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                Room room = layout[i][j];
                if (room != null) {

                    WallsAndFloors.drawWallsAndFloors(gc, safeZoneW, roomSize, room, i, j);

                    gc.setFill(Color.ALICEBLUE);

                    DoorsAndBlockers.drawAllDoorsAndBlockers(gc, room, safeZoneW, roomSize, i, j);

                    // Lights - in corner
                    // There is a maximum of 4 items (lights, heater, AC) that will be placed in the corners
                    Corner.drawCornerItems(gc, safeZoneW, roomSize, room, i, j);

                    // User - under text there is visual maximum of individual inside of the same room, or else they wont be drawn
                    Individuals.drawIndividuals(gc, safeZoneW, roomSize, room, i, j);
                }
            }
        }
    }

    /**
     * Draw layout
     */
    public void drawLayout() {
        if (nullHouse) {
            DrawingNull.drawNullLayout(gc);
            return;
        }

        double WIDTH = 500;
        double HEIGHT = 500;
        gc.drawImage(grass, 0, 0, WIDTH, HEIGHT);
        Images.drawImagesLayout(layout, gc, safeZoneW, roomSize);
        drawAllRooms();
    }
}
