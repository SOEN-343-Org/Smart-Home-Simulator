package org.soen343.models.permissions;

import java.util.ArrayList;

public class SHCcommands extends CommandsContract {

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
        String windowPermissions = "Family Adults have access to \n" +
                "window commands regardless of their location. \n" +
                "Guests and Family Children must be \n" +
                "within the room that the window \n" +
                "belongs to.";
        String doorPermissions = "Family Adults have access to \n" +
                "door commands regardless of their location. \n" +
                "Guests and Family Children must be \n" +
                "within the room that the door \n" +
                "belongs to, to access the commands.";
        String lightPermissions = "Family Adults have access to \n" +
                "light commands regardless of their location. \n" +
                "Guests and Family Children must be \n" +
                "within the room that the light \n" +
                "belongs to, to access the commands.";
        String autoModePermissions = "Family Adults have access to \n" +
                "Auto Mode commands regardless of their location. \n" +
                "Guests and Family Children must be \n" +
                "within the house to access Auto Mode Commands.";

        contract.add(new Command("Open / Close Windows", windowPermissions));
        contract.add(new Command("(Un)Lock Doors", doorPermissions));
        contract.add(new Command("Turn On / Off Lights", lightPermissions));
        contract.add(new Command("Turn On / Off Auto Mode", autoModePermissions));

    }
}
