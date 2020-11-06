package org.soen343.models.permissions;

import org.junit.jupiter.api.Test;
import org.soen343.models.User;
import org.soen343.models.house.Individual;

import static org.junit.jupiter.api.Assertions.*;

class SHCUserWindowRuleTest {

    SHCUserDoorRule SHCWindowTest = new SHCUserDoorRule();


    @Test
    void validateTest() {
        Individual testIdividual = new Individual(5, "TestName", "Family Adult", "outside", "TestUserName");
        User.setCurrentIndividual(testIdividual);
        assertTrue(SHCWindowTest.validate(5));
        testIdividual.setRole("Stranger");
        assertFalse(SHCWindowTest.validate(5));
        testIdividual.setLocation("kitchen");
        testIdividual.setRole("Family Child");
        //cant test other cases
    }
}