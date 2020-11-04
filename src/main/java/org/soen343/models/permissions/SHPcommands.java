package org.soen343.models.permissions;

import java.util.ArrayList;

/**
 * The type Sh pcommands.
 */
public class SHPcommands extends CommandsContract {

    /**
     * Instantiates a new SHPcommands.
     */
    public SHPcommands() {
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
        String awayModePermissions = "Family Adults and Family Children have access to Away Mode \n" +
                "commands, but they must not be inside the house. Guests and Strangers \n" +
                "do not have access to Away Mode commands.";

        contract.add(new Command("Open / Close Windows", awayModePermissions));

    }
}

