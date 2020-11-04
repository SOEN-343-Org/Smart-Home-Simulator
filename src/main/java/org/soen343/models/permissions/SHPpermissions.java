package org.soen343.models.permissions;

import java.util.ArrayList;

/**
 * The type SHPpermissions.
 */
public class SHPpermissions extends PermissionsContract{

    /**
     * Instantiates a new SHPpermissions.
     */
    public SHPpermissions() {
        contract = new ArrayList<Permission>();
        setContract();
    }

    @Override
    public ArrayList<Permission> getContract() {
        return contract;
    }

    @Override
    public void setContract() {
        // Add a newline every 6 words bc table doesn't adjust for us
        String familyAdultPermissions = "All permissions granted to set away mode \non/off when they are away";
        String familyChildPermissions = "All permissions granted to set away mode \non/off when they are away";
        String guestPermissions = "Guests do not have permissions to \nset away mode on/off";
        String strangerPermissions = "Non identified users have no permissions \nto set away mode on/off";

        contract.add(new Permission("Family Adult", familyAdultPermissions));
        contract.add(new Permission("Family Child", familyChildPermissions));
        contract.add(new Permission("Guest", guestPermissions));
        contract.add(new Permission("Stranger", strangerPermissions));
    }
}
