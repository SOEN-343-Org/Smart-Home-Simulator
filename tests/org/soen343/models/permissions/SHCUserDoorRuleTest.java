package org.soen343.models.permissions;

import org.junit.jupiter.api.Test;
import org.soen343.models.Model;
import org.soen343.models.User;
import org.soen343.models.house.Individual;

import static org.junit.jupiter.api.Assertions.*;

class SHCUserDoorRuleTest {

    SHCUserDoorRule SHCDoorTest = new SHCUserDoorRule();

    @Test
    void validateTest() {
        Model.setModelParameters();
        Individual testIndividual = new Individual(5, "TestName", "Family Adult", "outside", "TestUserName");
        User.setCurrentIndividual(testIndividual);
        assertTrue(SHCDoorTest.validate(testIndividual.getId()));
        testIndividual.setRole("Stranger");
        assertFalse(SHCDoorTest.validate(testIndividual.getId()));
        testIndividual.setLocation("kitchen");
        testIndividual.setRole("Family Child");
        assertFalse(SHCDoorTest.validate(testIndividual.getId()));
        testIndividual.setLocation("dining room");
        assertTrue(SHCDoorTest.validate(testIndividual.getId()));
    }
}