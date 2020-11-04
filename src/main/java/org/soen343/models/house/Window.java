package org.soen343.models.house;

<<<<<<< HEAD
public class Window implements Components {
=======
import org.soen343.models.permissions.Rule;
import org.soen343.models.permissions.SHCRule;
import org.soen343.models.permissions.Validator;

public class Window implements Components, Validator {
>>>>>>> validation done for SHC commands according to user role and location

    private final int id;
    private boolean open;
    private boolean blocked;

    /**
     * Creates a Window
     *
     * @param id id of the window
     */
    public Window(int id) {
        this.id = id;
        this.open = false;
        this.blocked = false;
    }

    /**
     * Gets the id of the window
     *
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the opened state of the window
     *
     * @return True if the window is opened
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Sets the state 'opened' of the window
     *
     * @param open new opened state of the window
     */
    public void setOpen(boolean open) {
        if (validate() == true) {
            this.open = open;
        }
    }

    /**
     * Gets the blocked state of the window
     *
     * @return True if the window is blocked
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Sets the state 'blocked' of the window
     *
     * @param blocked new blocked state of the window
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * Gets the name of the window
     *
     * @return string name
     */
    public String getName() {
        return "Window #" + id;
    }

    @Override
    public String toString() {
        return this.getName() + ", state: " + (open ? "opened" : "closed") + (blocked ? "blocked" : "not blocked");
    }

    @Override
    public boolean validate() {
        Rule r = new SHCRule();
        Rule windowRule = r.createRule("Window", id);
        boolean isValid = windowRule.validate();
        if (isValid) return true;
        return false;
    }
}
