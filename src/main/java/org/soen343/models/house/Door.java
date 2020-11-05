package org.soen343.models.house;

public class Door implements Components {

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
    public void setOpen(boolean open) { this.open = open; }

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
}
