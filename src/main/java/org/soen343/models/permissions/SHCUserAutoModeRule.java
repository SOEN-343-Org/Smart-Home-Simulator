package org.soen343.models.permissions;

import org.soen343.models.User;

/**
 * The type Shc user auto mode rule.
 */
public class SHCUserAutoModeRule extends SHCRule{

    private int id;

    /**
     * Instantiates a new Shc user auto mode rule.
     *
     * @param id the id
     */
    SHCUserAutoModeRule(int id) {
        this.id = id;
    }

    @Override
    public boolean validate() {
        String individualLocation = User.getCurrentIndividual().getLocation();
        String role = User.getCurrentIndividual().getRole();
        boolean userInHouse = !individualLocation.equals("outside");

        if (role.equals("Family Adult")) return true;
        if (role.equals("Family Child") && userInHouse) return true;
        if (role.equals("Guest") && userInHouse) return true;

        // TODO : Log that the user is not allowed to issue this command
        System.out.println( role + " NOT ALLOWED to issue this command");

        return false;
    }
}
