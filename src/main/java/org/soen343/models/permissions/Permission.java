package org.soen343.models.permissions;

/**
 * The type Permission.
 */
public class Permission {

    private String role;
    private String description;

    /**
     * Instantiates a new Permission.
     *
     * @param role        the role could be a profile or module
     * @param description the description
     */
    Permission(String role, String description) {
        this.role = role;
        this.description = description;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
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
        return "Permission{" +
                "role='" + role + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
