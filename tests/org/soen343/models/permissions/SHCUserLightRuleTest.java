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
        Individual testIndividual = new Individual(5, "TestName", "Family Adult", "outside", "TestUserName");
        User.setCurrentIndividual(testIndividual);
        assertTrue(SHCLightTest.validate(testIndividual.getId()));
        testIndividual.setRole("Stranger");
        assertFalse(SHCLightTest.validate(testIndividual.getId()));
        testIndividual.setRole("Family Child");
        assertFalse(SHCLightTest.validate(testIndividual.getId()));
        testIndividual.setLocation("kitchen");
        assertFalse(SHCLightTest.validate(testIndividual.getId()));
        testIndividual.setLocation("dining room");
        assertTrue(SHCLightTest.validate(testIndividual.getId()));

    }
}