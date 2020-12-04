package org.soen343.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.soen343.controllers.HouseLayoutController;
import org.soen343.models.house.Light;
import org.soen343.models.house.Room;

import java.util.ArrayList;

public class Corner {

    private static Image openedLight;
    private static Image closedLight;
    private static final double iconSize = 35;
    private static final double SAFE_ZONE_H = 35;

    static int currentObjectPosition = 0;

    public static void intitializeImages(){
        openedLight = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/opened_light.png")));
        closedLight = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/closed_light.png")));
    }

    public static void drawCornerItems(GraphicsContext gc, double safeZoneW, double roomSize, Room room, int i, int j){
        ArrayList<Light> lights = room.getLights();
        for (Light light : lights) {
            switch (currentObjectPosition){
                case 0:
                    drawCorner0(gc, safeZoneW, roomSize, light, i ,j);
                    break;
                case 1:
                    drawCorner1(gc, safeZoneW, roomSize, light, i, j);
                    break;
                case 2:
                    drawCorner2(gc, safeZoneW, roomSize, light, i, j);
                    break;
                case 3:
                    drawCorner3(gc, safeZoneW, roomSize, light, i, j);
                    break;
            }
        }
    }

    public static void drawCorner3(GraphicsContext gc, double safeZoneW, double roomSize, Light light, int i, int j){
        gc.drawImage((light.isOpen() ? openedLight : closedLight), (safeZoneW + roomSize * j) + roomSize - iconSize, (SAFE_ZONE_H + roomSize * i) + roomSize - iconSize, iconSize, iconSize);
        gc.setFill(light.isOpen() ? Color.BLACK : Color.ALICEBLUE);
        gc.fillText(Integer.toString(light.getId()), (safeZoneW + roomSize * j) + roomSize - iconSize * 0.7, (SAFE_ZONE_H + roomSize * i) + roomSize - iconSize / 3.0);
    }

    public static void drawCorner2(GraphicsContext gc, double safeZoneW, double roomSize, Light light, int i, int j){
        currentObjectPosition++;
        gc.drawImage((light.isOpen() ? openedLight : closedLight), (safeZoneW + roomSize * j), (SAFE_ZONE_H + roomSize * i) + roomSize - iconSize, iconSize, iconSize);
        gc.setFill(light.isOpen() ? Color.BLACK : Color.ALICEBLUE);
        gc.fillText(Integer.toString(light.getId()), (safeZoneW + roomSize * j) + iconSize / 3.0, (SAFE_ZONE_H + roomSize * i) + roomSize - iconSize / 3.0);
    }

    public static void drawCorner1(GraphicsContext gc, double safeZoneW, double roomSize, Light light, int i, int j){
        currentObjectPosition++;
        gc.drawImage((light.isOpen() ? openedLight : closedLight), (safeZoneW + roomSize * j) + roomSize - iconSize, (SAFE_ZONE_H + roomSize * i), iconSize, iconSize);
        gc.setFill(light.isOpen() ? Color.BLACK : Color.ALICEBLUE);
        gc.fillText(Integer.toString(light.getId()), (safeZoneW + roomSize * j) + roomSize - iconSize * 0.7, (SAFE_ZONE_H + roomSize * i) + iconSize * 0.7);
    }

    public static void drawCorner0(GraphicsContext gc, double safeZoneW, double roomSize, Light light, int i, int j){
        currentObjectPosition++;
        gc.drawImage((light.isOpen() ? openedLight : closedLight), (safeZoneW + roomSize * j), (SAFE_ZONE_H + roomSize * i), iconSize, iconSize);
        gc.setFill(light.isOpen() ? Color.BLACK : Color.ALICEBLUE);
        gc.fillText(Integer.toString(light.getId()), (safeZoneW + roomSize * j) + iconSize / 3.0, (SAFE_ZONE_H + roomSize * i) + iconSize * 0.7);
    }

}
