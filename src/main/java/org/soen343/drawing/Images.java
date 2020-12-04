package org.soen343.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.soen343.controllers.HouseLayoutController;
import org.soen343.models.house.Room;

public class Images {

    private static final double SAFE_ZONE_H = 35;
    private static Image floor;

    public static void intitializeImages(){
        floor = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/floor.jpg")));
    }


    public static void drawImagesLayout(Room[][] layout, GraphicsContext gc, double safeZoneW, double roomSize){
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] != null) {
                    gc.drawImage(floor, safeZoneW + roomSize * j, SAFE_ZONE_H + roomSize * i, roomSize, roomSize);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(5.0);
                    gc.strokeRect(safeZoneW + roomSize * j, SAFE_ZONE_H + roomSize * i, roomSize, roomSize);
                }
            }
        }
    }

}
