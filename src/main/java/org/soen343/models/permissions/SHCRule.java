package org.soen343.models.permissions;

/**
 * The type Shc rule.
 */
public class SHCRule extends Rule {

    public Rule createRule(String component, int id) {
        if (component.equals("Door")) {
            return new SHCUserDoorRule(id);
        } else if (component.equals("Light")) {
            return new SHCUserLightRule(id);
        } else if (component.equals("Window")) {
            return new SHCUserWindowRule(id);
        } else if (component.equals("AutoMode")) {
            return new SHCUserAutoModeRule(id);
        }
        return null;
    }

    // not to be used
    @Override
    public boolean validate(int id) {
        return false;
    }
}
