package org.soen343.models.permissions;

/**
 * The type Shc rule.
 */
public class SHCRule extends Rule {

    public Rule createRule(String component, int id) {
        if (component.equals("Door")) {
            return new SHCUserDoorRule();
        } else if (component.equals("Light")) {
            return new SHCUserLightRule();
        } else if (component.equals("Window")) {
            return new SHCUserWindowRule();
        } else if (component.equals("AutoMode")) {
            return new SHCUserAutoModeRule();
        }
        return null;
    }

    // not to be used
    @Override
    public boolean validate(int id) {
        return false;
    }
}
