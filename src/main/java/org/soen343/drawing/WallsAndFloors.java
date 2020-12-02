package org.soen343.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.soen343.models.house.Room;

public class WallsAndFloors {

    private static final double SAFE_ZONE_H = 35;

    public static void drawWallsAndFloors(GraphicsContext gc, double safeZoneW, double roomSize,Room room, int i, int j){
        // Draw floor, walls and name of room
        double fontSize = 20;
        gc.setFont(new Font(fontSize));
        gc.setFill(Color.ALICEBLUE);
        Text text = new Text(room.getName());
        text.setFont(new Font(fontSize));
        double textSize = text.getBoundsInLocal().getWidth();
        gc.fillText(room.getName(), (safeZoneW + roomSize * j) + (roomSize - textSize) / 2, (SAFE_ZONE_H + roomSize * i) + (roomSize / 2));
    }

}
