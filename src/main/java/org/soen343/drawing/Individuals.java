package org.soen343.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.soen343.controllers.HouseLayoutController;
import org.soen343.models.Model;
import org.soen343.models.house.Individual;
import org.soen343.models.house.Room;

import java.util.ArrayList;

public class Individuals {

    private static Image individual;
    private static final double iconSize = 35;
    private static final double SAFE_ZONE_H = 35;

    public static void intitializeImages(){
        individual = new Image(String.valueOf(HouseLayoutController.class.getResource("/org/soen343/img/individual.png")));
    }

    public static void drawIndividuals(GraphicsContext gc, double safeZoneW, double roomSize, Room room, int i, int j){
        ArrayList<Individual> individuals = Model.getHouse().getIndividuals();
        int currentIndividualPosition = 0;
        for (Individual ind : individuals) {
            if (ind.getLocation().equals(room.getName()) && currentIndividualPosition < 4) {
                // Draw individuals
                gc.drawImage(individual, (safeZoneW + roomSize * j) + (currentIndividualPosition * iconSize) + 5, (SAFE_ZONE_H + roomSize * i) + (roomSize / 2.0), iconSize, iconSize);
                gc.setFill(Color.ALICEBLUE);
                gc.fillText(Integer.toString(ind.getId()), (safeZoneW + roomSize * j) + (iconSize / 2.0) - 5 + (currentIndividualPosition * iconSize), (SAFE_ZONE_H + roomSize * i) + (roomSize / 2) + iconSize);
                currentIndividualPosition++;
            }
        }
    }
}
