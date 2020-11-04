package org.soen343.models.house;

<<<<<<< HEAD
public class Door implements Components {
=======
import org.soen343.models.permissions.Rule;
import org.soen343.models.permissions.SHCRule;
import org.soen343.models.permissions.Validator;

public class Door implements Components, Validator {
>>>>>>> validation done for SHC commands according to user role and location

    private final int id;
    private boolean open;

    /**
     * Creates a Door object with id id
     *
     * @param id int id
     */
    public Door(int id) {
        this.id = id;
        this.open = false;
    }

    /**
     * @return True if the door is opened
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Set state of the door
     *
     * @param open state of the door
     */
    public void setOpen(boolean open) {
        if (validate() == true) {
            this.open = open;
        }
    }

    /**
     * Gets the id of the door
     *
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the door
     *
     * @return string name
     */
    public String getName() {
        return "Door #" + id;
    }

    @Override
    public String toString() {
        return this.getName() + ", state: " + (open ? "opened" : "closed");
    }

    @Override
    public boolean validate() {
        Rule r = new SHCRule();
        Rule doorRule = r.createRule("Door", id);
        boolean isValid = doorRule.validate();
        if (isValid) return true;
        return false;
    }
}
