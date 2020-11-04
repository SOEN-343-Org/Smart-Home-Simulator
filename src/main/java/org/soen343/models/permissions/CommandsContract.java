package org.soen343.models.permissions;

import java.util.ArrayList;

/**
 * The type Commands contract.
 */
public abstract class CommandsContract {
    /**
     * The Contract is a list of Command objects.
     */
    public ArrayList<Command> contract;

    /**
     * Gets contract.
     *
     * @return the contract
     */
    abstract public ArrayList<Command> getContract();

    /**
     * Sets contract.
     */
    abstract void setContract();
}
