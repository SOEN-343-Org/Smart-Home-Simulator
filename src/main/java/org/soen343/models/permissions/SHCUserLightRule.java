package org.soen343.models.permissions;

import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Room;

/**
 * The type Shc user light rule.
 */
public class SHCUserLightRule extends SHCRule{
    private int id;

    /**
     * Instantiates a new Shc user light rule.
     *
     * @param id the id
     */
    SHCUserLightRule(int id) {
        this.id = id;
    }

    @Override
    public boolean validate() {
        Room roomWithLight = Model.getHouse().getRoomByLightId(id);
        String individualLocation = User.getCurrentIndividual().getLocation();
        String role = User.getCurrentIndividual().getRole();
        boolean autoModeIsOn = Model.getSimulationParameters().isAutoModeOn();
        boolean userInRoom = false;
        boolean userInHouse = !individualLocation.equals("outside");

        if (individualLocation.equals(roomWithLight.getName())) {
            userInRoom = true;
        }
        if (role.equals("Family Adult")) return true;
        if (role.equals("Family Child") && userInRoom)  return true;
        if (role.equals("Guest") && userInRoom) return true;
        if (role.equals("Family Child") && userInHouse && autoModeIsOn) return true;
        if (role.equals("Guest") && userInHouse && autoModeIsOn) return true;

        // TODO : Log that the user is not allowed to issue this command

        return false;
    }
}
