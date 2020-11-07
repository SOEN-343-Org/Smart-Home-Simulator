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
        Individual testIdividual = new Individual(5, "TestName", "Family Adult", "outside", "TestUserName");
        User.setCurrentIndividual(testIdividual);
        assertTrue(SHCDoorTest.validate(5));
        testIdividual.setRole("Stranger");
        assertFalse(SHCDoorTest.validate(5));
        testIdividual.setLocation("kitchen");
        testIdividual.setRole("Family Child");
        assertFalse(SHCDoorTest.validate(testIdividual.getId()));
        testIdividual.setLocation("dining room");
        assertTrue(SHCDoorTest.validate(testIdividual.getId()));
    }
}