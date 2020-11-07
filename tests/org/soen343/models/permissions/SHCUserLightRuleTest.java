package org.soen343.models.permissions;

import org.junit.jupiter.api.Test;
import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;

import static org.junit.jupiter.api.Assertions.*;

class SHCUserLightRuleTest {

    SHCUserLightRule SHCLightTest = new SHCUserLightRule();

    @Test
    void validateTest() {
        Model.setModelParameters();
        Individual testIdividual = new Individual(5, "TestName", "Family Adult", "outside", "TestUserName");
        User.setCurrentIndividual(testIdividual);
        assertTrue(SHCLightTest.validate(testIdividual.getId()));
        testIdividual.setRole("Stranger");
        assertFalse(SHCLightTest.validate(testIdividual.getId()));
        testIdividual.setRole("Family Child");
        assertFalse(SHCLightTest.validate(testIdividual.getId()));
        testIdividual.setLocation("kitchen");
        assertFalse(SHCLightTest.validate(testIdividual.getId()));
        testIdividual.setLocation("dining room");
        assertTrue(SHCLightTest.validate(testIdividual.getId()));

    }
}