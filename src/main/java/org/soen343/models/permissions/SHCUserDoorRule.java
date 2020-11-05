package org.soen343.models.permissions;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Room;

import java.util.ArrayList;

/**
 * The type Shc user door rule.
 */
public class SHCUserDoorRule extends SHCRule {

    public SHCUserDoorRule() {}

    @Override
    public boolean validate(int id) {
        String role = User.getCurrentIndividual().getRole();
        if (role.equals("Family Adult")) return true;
        if (role.equals("Stranger")) return false;

        ArrayList<Room> roomsWithDoor = Model.getHouse().getRoomByDoorId(id);
        String individualLocation = User.getCurrentIndividual().getLocation();
        boolean userInRoom = false;

        for (Room room : roomsWithDoor) {
            if (individualLocation.equals(room.getName())) {
                userInRoom = true;
                break;
            }
        }
        if (role.equals("Family Child") && userInRoom) return true;
        if (role.equals("Guest") && userInRoom) return true;

        return false;
    }
}
