package org.soen343.models.permissions;

import java.util.ArrayList;

/**
 * The type Permissions contract.
 */
public abstract class PermissionsContract {
    /**
     * The Contract is a list of Permission objects.
     */
    public ArrayList<Permission> contract;

    /**
     * Gets contract.
     *
     * @return the contract
     */
    abstract public ArrayList<Permission> getContract();

    /**
     * Sets contract.
     */
    abstract void setContract();
}
