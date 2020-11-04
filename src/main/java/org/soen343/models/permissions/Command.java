package org.soen343.models.permissions;

public class Command {

    private String name;
    private String description;

    /**
     * Instantiates a new Command.
     *
     * @param name        the name of the command
     * @param description the description
     */
    Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gets name of the command.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets role.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
