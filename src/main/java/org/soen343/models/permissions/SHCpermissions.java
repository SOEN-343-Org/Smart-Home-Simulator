package org.soen343.models.permissions;

import java.util.ArrayList;

/**
 * The type SHCpermissions.
 */
public class SHCpermissions extends PermissionsContract{

    /**
     * Instantiates a new SHCpermissions.
     */
    public SHCpermissions() {
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
        String familyAdultPermissions = "All permissions granted to open/close \nwindows, " +
                "unlock doors, open/close garage \nand turn on/off lights";
        String familyChildPermissions = "Limited permissions to turn on/off \nlights and " +
                "open/close windows on \nthe room that they are located. \nIf they are not at home, \n" +
                "all permissions are revoked";
        String guestPermissions = "Limited permissions to turn on/off \nlights and " +
                "open/close windows on \nthe room that they are located. \nIf they are not at home, \n" +
                "all permissions are revoked";
        String strangerPermissions = "Non identified users have no permissions \nno matter their location";

        contract.add(new Permission("Family Adult", familyAdultPermissions));
        contract.add(new Permission("Family Child", familyChildPermissions));
        contract.add(new Permission("Guest", guestPermissions));
        contract.add(new Permission("Stranger", strangerPermissions));

    }
}
