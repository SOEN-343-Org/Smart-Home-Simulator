package org.soen343.models.permissions;

import org.junit.jupiter.api.Test;
import org.soen343.models.User;
import org.soen343.models.house.Individual;

import static org.junit.jupiter.api.Assertions.*;

class SHCUserAutoModeRuleTest {

    SHCUserAutoModeRule SHCAutoTest = new SHCUserAutoModeRule();

    @Test
    void validateTest() {
        Individual testIdividual = new Individual(5, "TestName", "Family Adult", "outside", "TestUserName");
        User.setCurrentIndividual(testIdividual);
        assertTrue(SHCAutoTest.validate(5));
        testIdividual.setRole("Stranger");
        assertFalse(SHCAutoTest.validate(5));
        testIdividual.setRole("Family Child");
        testIdividual.setLocation("not outside");
        assertTrue(SHCAutoTest.validate(5));
        testIdividual.setRole("Guest");
        assertTrue(SHCAutoTest.validate(5));
        testIdividual.setLocation("outside");
        assertFalse(SHCAutoTest.validate(5));
    }
}