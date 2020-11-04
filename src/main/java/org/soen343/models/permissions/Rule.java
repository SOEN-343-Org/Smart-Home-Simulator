package org.soen343.models.permissions;

/**
 * The type Rule.
 */
public abstract class Rule implements Validator{

    /**
     * Create rule rule.
     *
     * @param component the component
     * @param id        the id
     * @return the rule
     */
    public abstract Rule createRule(String component, int id);
}
