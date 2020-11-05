package org.soen343.models.permissions;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Room;

import java.util.ArrayList;

/**
 * The type Shc user door rule.
 */
public class SHCUserDoorRule extends SHCRule {
    private int id;

    /**
     * Instantiates a new Shc user door rule.
     *
     * @param id the id
     */
    SHCUserDoorRule(int id) {
        this.id = id;
    }

    @Override
    public boolean validate(int id) {
        ArrayList<Room> roomsWithDoor = Model.getHouse().getRoomByDoorId(id);
        String individualLocation = User.getCurrentIndividual().getLocation();
        String role = User.getCurrentIndividual().getRole();
        boolean userInRoom = false;

        for (Room room : roomsWithDoor) {
            if (individualLocation.equals(room.getName())) {
                userInRoom = true;
                break;
            }
        }
        if (role.equals("Family Adult")) return true;
        if (role.equals("Family Child") && userInRoom) return true;
        if (role.equals("Guest") && userInRoom) return true;

        return false;
    }
}
