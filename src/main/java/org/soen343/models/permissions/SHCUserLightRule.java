package org.soen343.models.permissions;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Room;

/**
 * The type Shc user light rule.
 */
public class SHCUserLightRule extends SHCRule{

    public SHCUserLightRule() { }

    @Override
    public boolean validate(int id) {
        String role = User.getCurrentIndividual().getRole();
        if (role.equals("Family Adult")) return true;
        if (role.equals("Stranger")) return false;

        Room roomWithLight = Model.getHouse().getRoomByLightId(id);
        String individualLocation = User.getCurrentIndividual().getLocation();
        boolean userInRoom = false;

        if (individualLocation.equals(roomWithLight.getName())) {
            userInRoom = true;
        }
        if (role.equals("Family Child") && userInRoom)  return true;
        if (role.equals("Guest") && userInRoom) return true;

        return false;
    }
}
