package org.soen343.models.permissions;

import org.junit.jupiter.api.Test;
import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;

import static org.junit.jupiter.api.Assertions.*;


class SHCUserWindowRuleTest {

    SHCUserWindowRule SHCWindowTest = new SHCUserWindowRule();

    @Test
    void validateTest() {
        Model.setModelParameters();
        Individual testIdividual = new Individual(5, "TestName", "Family Adult", "outside", "TestUserName");
        User.setCurrentIndividual(testIdividual);
        assertTrue(SHCWindowTest.validate(testIdividual.getId()));
        testIdividual.setRole("Stranger");
        assertFalse(SHCWindowTest.validate(testIdividual.getId()));
        testIdividual.setRole("Family Child");
        assertFalse(SHCWindowTest.validate(testIdividual.getId()));
        testIdividual.setLocation("kitchen");
        assertTrue(SHCWindowTest.validate(testIdividual.getId()));
    }
}