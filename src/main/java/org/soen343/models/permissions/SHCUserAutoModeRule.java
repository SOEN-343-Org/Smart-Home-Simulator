package org.soen343.models.permissions;

import org.soen343.models.User;

/**
 * The type Shc user auto mode rule.
 */
public class SHCUserAutoModeRule extends SHCRule{

    SHCUserAutoModeRule() {  }

    @Override
    public boolean validate(int id) {
        String role = User.getCurrentIndividual().getRole();
        if (role.equals("Family Adult")) return true;
        String individualLocation = User.getCurrentIndividual().getLocation();
        boolean userInHouse = !individualLocation.equals("outside");

        if (role.equals("Family Child") && userInHouse) return true;
        if (role.equals("Guest") && userInHouse) return true;

        return false;
    }
}
