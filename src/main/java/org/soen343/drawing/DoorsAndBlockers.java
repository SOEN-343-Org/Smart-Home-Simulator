package org.soen343.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.soen343.controllers.HouseLayoutController;
import org.soen343.models.house.Door;
import org.soen343.models.house.Room;
import org.soen343.models.house.Window;

public class DoorsAndBlockers {

    private static final double iconSize = 35;
    private static final double SAFE_ZONE_H = 35;



    private static Image openedDoor;
    private static Image closedDoor;
    private static Image openedWindow;
    private static Image closedWindow;
    private static Image blocker;

    public static void initalizeImages() {
        openedDoor = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/opened_door.png")));
        closedDoor = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/closed_door.png")));
        openedWindow = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/opened_window.png")));
        closedWindow = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/closed_window.png")));
        blocker = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/blocker.png")));
    }

    public static void drawAllDoorsAndBlockers(GraphicsContext gc, Room room,double safeZoneW, double roomSize, int i, int j){
        drawTopDoorsAndBlockers(gc, safeZoneW, roomSize, room, i, j);
        drawRightDoorsAndBlockers(gc, safeZoneW, roomSize, room, i, j);
        drawBottomDoorsAndBlockers(gc, safeZoneW, roomSize, room, i, j);
        drawLeftDoorsAndBlockers(gc, safeZoneW, roomSize, room, i, j);
    }

    public static void drawTopDoorsAndBlockers(GraphicsContext gc, double safeZoneW, double roomSize, Room room, int i, int j){
        double safeZoneWSize = (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0;
        if (room.getTop() != null && isDoor(room.getTop())) {
            gc.drawImage((((Door) room.getTop()).isOpen() ? openedDoor : closedDoor), safeZoneWSize, (SAFE_ZONE_H + roomSize * i) - (iconSize / 2.0), iconSize, iconSize);
            gc.fillText(Integer.toString(((Door) room.getTop()).getId()), (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0 + iconSize / 2.0, (SAFE_ZONE_H + roomSize * i) + iconSize / 4.0);
        } else if (room.getTop() != null && !isDoor(room.getTop())) {
            gc.drawImage((((Window) room.getTop()).isOpen() ? openedWindow : closedWindow), safeZoneWSize, (SAFE_ZONE_H + roomSize * i) - (iconSize / 2.0), iconSize, iconSize);
            gc.fillText(Integer.toString(((Window) room.getTop()).getId()), (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0 + iconSize / 3.0, (SAFE_ZONE_H + roomSize * i) + iconSize / 4.0);
            if (((Window) room.getTop()).isBlocked()) {
                gc.drawImage(blocker, safeZoneWSize, (SAFE_ZONE_H + roomSize * i) + iconSize / 2.0, iconSize, iconSize);
            }
        }
    }

    public static void drawRightDoorsAndBlockers(GraphicsContext gc, double safeZoneW, double roomSize, Room room, int i, int j){
        double safeZoneWSize = (safeZoneW + roomSize * j) + (roomSize - iconSize / 2.0);
        double safeZoneHSize = (SAFE_ZONE_H + roomSize * i) + (roomSize - iconSize) / 2.0;
        double finalSize = (SAFE_ZONE_H + roomSize * i) + (roomSize / 2.0 + iconSize / 4.0);
        if (room.getRight() != null && isDoor(room.getRight())) {
            gc.drawImage((((Door) room.getRight()).isOpen() ? openedDoor : closedDoor), safeZoneWSize, safeZoneHSize, iconSize, iconSize);
            gc.fillText(Integer.toString(((Door) room.getRight()).getId()), (safeZoneW + roomSize * j) + roomSize, finalSize);
        } else if (room.getRight() != null && !isDoor(room.getRight())) {
            gc.drawImage((((Window) room.getRight()).isOpen() ? openedWindow : closedWindow), safeZoneWSize, safeZoneHSize, iconSize, iconSize);
            gc.fillText(Integer.toString(((Window) room.getRight()).getId()), (safeZoneW + roomSize * j) + (roomSize - iconSize / 8.0), finalSize);
            if (((Window) room.getRight()).isBlocked()) {
                gc.drawImage(blocker, safeZoneWSize - iconSize, safeZoneHSize, iconSize, iconSize);
            }
        }
    }

    public static void drawBottomDoorsAndBlockers(GraphicsContext gc, double safeZoneW, double roomSize, Room room, int i, int j){
        double safeZoneWSize = (safeZoneW + roomSize * j) + (roomSize - iconSize) / 2.0;
        double safeZoneHSize = (SAFE_ZONE_H + roomSize * i) + (roomSize - iconSize / 2.0);
        double finalSize = (SAFE_ZONE_H + roomSize * i) + roomSize + iconSize / 4.0;
        if (room.getDown() != null && isDoor(room.getDown())) {
            gc.drawImage((((Door) room.getDown()).isOpen() ? openedDoor : closedDoor), safeZoneWSize, safeZoneHSize, iconSize, iconSize);
            gc.fillText(Integer.toString(((Door) room.getDown()).getId()), (safeZoneW + roomSize * j) + (roomSize / 2.0), finalSize);
        } else if (room.getDown() != null && !isDoor(room.getDown())) {
            gc.drawImage((((Window) room.getDown()).isOpen() ? openedWindow : closedWindow), safeZoneWSize, safeZoneHSize, iconSize, iconSize);
            gc.fillText(Integer.toString(((Window) room.getDown()).getId()), (safeZoneW + roomSize * j) + (roomSize - iconSize / 3.0) / 2.0, finalSize);
            if (((Window) room.getDown()).isBlocked()) {
                gc.drawImage(blocker, safeZoneWSize, safeZoneHSize - iconSize, iconSize, iconSize);
            }
        }
    }

    public static void drawLeftDoorsAndBlockers(GraphicsContext gc, double safeZoneW, double roomSize, Room room, int i, int j){
        double safeZoneWSize = (safeZoneW + roomSize * j) - (iconSize / 2.0);
        double safeZoneHSize = (SAFE_ZONE_H + roomSize * i) + (roomSize - iconSize) / 2.0;
        double finalSize = (SAFE_ZONE_H + roomSize * i) + (roomSize / 2.0 + iconSize / 4.0);
        if (room.getLeft() != null && isDoor(room.getLeft())) {
            gc.drawImage((((Door) room.getLeft()).isOpen() ? openedDoor : closedDoor), safeZoneWSize, safeZoneHSize, iconSize, iconSize);
            gc.fillText(Integer.toString(((Door) room.getLeft()).getId()), (safeZoneW + roomSize * j), finalSize);
        } else if (room.getLeft() != null && !isDoor(room.getLeft())) {
            gc.drawImage((((Window) room.getLeft()).isOpen() ? openedWindow : closedWindow), safeZoneWSize, safeZoneHSize, iconSize, iconSize);
            gc.fillText(Integer.toString(((Window) room.getLeft()).getId()), (safeZoneW + roomSize * j) - iconSize / 9.0, finalSize);
            if (((Window) room.getLeft()).isBlocked()) {
                gc.drawImage(blocker, safeZoneWSize + iconSize, safeZoneHSize, iconSize, iconSize);
            }
        }
    }

    /**
     * Create door
     *
     * @param object
     * @return object door
     */
    private static boolean isDoor(Object object) {
        // Not the best and prettiest way but there is no place to put this yet.
        return object instanceof Door;
    }

}
