package org.soen343.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.soen343.controllers.HouseLayoutController;
import org.soen343.models.house.Room;

public class ACAndHeater {
    private static final double iconSize = 35;
    private static final double SAFE_ZONE_H = 35;

    private static Image heater;
    private static Image ac;


    public static void initalizeImages() {
        heater = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/heater.png")));
        ac = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/ac.png")));
    }

    public static void drawElectrics(GraphicsContext gc, double safeZoneW, double roomSize, Room room, int i, int j) {

        if (room.getHeater().isOn()) {
            gc.drawImage(heater, (safeZoneW + roomSize * j) + 20, (SAFE_ZONE_H + roomSize * i) + iconSize / 2, iconSize, iconSize);
        }

        final double v1 = (SAFE_ZONE_H + roomSize * i) + roomSize / 4;
        if (room.getAC().isOn()) {
            gc.drawImage(ac, (safeZoneW + roomSize * j) + (roomSize / 3) * 2, v1, iconSize, iconSize);
        }

        double fontSize = 15;
        gc.setFont(new Font(fontSize));
        gc.setFill(Color.ALICEBLUE);
        Text text = new Text(String.format("%.2f", room.getTemperature()) + "°C");
        text.setFont(new Font(fontSize));
        double tempSize = text.getBoundsInLocal().getWidth();
        gc.fillText(String.format("%.2f", room.getTemperature()) + "°C", (safeZoneW + roomSize * j) + (roomSize - tempSize) / 2, v1);

    }
}
