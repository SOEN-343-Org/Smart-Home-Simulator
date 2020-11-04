package org.soen343.models.house;

<<<<<<< HEAD
public class Light implements Components {
=======
import org.soen343.models.permissions.Rule;
import org.soen343.models.permissions.SHCRule;
import org.soen343.models.permissions.Validator;

public class Light implements Components, Validator {
>>>>>>> validation done for SHC commands according to user role and location

    private final int id;
    private boolean open;

    /**
     * Creates a Light object
     *
     * @param id id of the light
     */
    public Light(int id) {
        this.id = id;
        this.open = false;
    }

    /**
     * Gets the state of the light
     *
     * @return True if the light is opened
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Sets the state of the light
     *
     * @param open new state of the light
     */
    public void setOpen(boolean open) {
        if (validate() == true) {
            this.open = open;
        }
    }

    /**
     * Gets the id of the light
     *
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the light
     *
     * @return string name
     */
    public String getName() {
        return "Light #" + id;
    }

    @Override
    public String toString() {
        return this.getName() + ", state: " + (open ? "opened" : "closed");
    }

    @Override
    public boolean validate() {
        Rule r = new SHCRule();
        Rule lightRule = r.createRule("Light", id);
        boolean isValid = lightRule.validate();
        if (isValid) return true;
        return false;
    }
}
