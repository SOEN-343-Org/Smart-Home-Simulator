package org.soen343.models.permissions;

import java.util.ArrayList;

/**
 * The type Sh ccommands.
 */
public class SHCcommands extends CommandsContract {

    /**
     * Instantiates a new SHCcommands.
     */
    public SHCcommands() {
        contract = new ArrayList<Command>();
        setContract();
    }

    @Override
    public ArrayList<Command> getContract() {
        return contract;
    }

    @Override
    public void setContract() {
        // Add a newline every 6 words bc table doesn't adjust for us
        String windowPermissions = "Family Adults have access to window commands regardless of their \n" +
                "location. Guests and Family Children must be within the room that \n" +
                "the window belongs to.";
        String doorPermissions = "Family Adults have access to door commands regardless of their \n" +
                "location. Guests and Family Children must be within the room that the \n" +
                "door belongs to, to access the commands.";
        String lightPermissions = "Family Adults have access to light commands regardless of \n" +
                "their location. Guests and Family Children must be within the room that the \n" +
                "light belongs to, to access the commands.";
        String autoModePermissions = "Family Adults have access to Auto Mode commands regardless \n" +
                "of their location. Guests and Family Children must be within the house to \n" +
                "access Auto Mode Commands.";

        contract.add(new Command("Open / Close Windows", windowPermissions));
        contract.add(new Command("(Un)Lock Doors", doorPermissions));
        contract.add(new Command("Turn On / Off Lights", lightPermissions));
        contract.add(new Command("Turn On / Off Auto Mode", autoModePermissions));

    }
}
