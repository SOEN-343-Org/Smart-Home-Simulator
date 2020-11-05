package org.soen343.models.permissions;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Room;

/**
 * The type Shc user window rule.
 */
public class SHCUserWindowRule extends SHCRule{

    private int id;

    /**
     * Instantiates a new Shc user window rule.
     *
     * @param id the id
     */
    SHCUserWindowRule(int id) {
        this.id = id;
    }

    @Override
    public boolean validate(int id) {
        Room roomWithWindow = Model.getHouse().getRoomByWindowId(id);
        String individualLocation = User.getCurrentIndividual().getLocation();
        String role = User.getCurrentIndividual().getRole();
        boolean userInRoom = false;

        if (individualLocation.equals(roomWithWindow.getName())) {
            userInRoom = true;
        }
        if (role.equals("Family Adult")) return true;
        if (role.equals("Family Child") && userInRoom) return true;
        if (role.equals("Guest") && userInRoom) return true;

        return false;
    }
}
