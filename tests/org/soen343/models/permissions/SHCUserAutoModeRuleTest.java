package org.soen343.models.permissions;

import org.junit.jupiter.api.Test;
import org.soen343.models.User;
import org.soen343.models.house.Individual;

import static org.junit.jupiter.api.Assertions.*;

class SHCUserAutoModeRuleTest {

    SHCUserAutoModeRule SHCTest = new SHCUserAutoModeRule();

    @Test
    void validateTest() {
        Individual testIdividual = new Individual(5, "TestName", "Family Adult", "outside", "TestUserName");
        User.setCurrentIndividual(testIdividual);
        assertTrue(SHCTest.validate(5));
        testIdividual.setRole("Stranger");
        assertFalse(SHCTest.validate(5));
        testIdividual.setRole("Family Child");
        testIdividual.setLocation("not outside");
        assertTrue(SHCTest.validate(5));
        testIdividual.setRole("Guest");
        assertTrue(SHCTest.validate(5));
        testIdividual.setLocation("outside");
        assertFalse(SHCTest.validate(5));
    }
}