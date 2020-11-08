package org.soen343.models.permissions;

import org.junit.jupiter.api.Test;
import org.soen343.models.User;
import org.soen343.models.house.Individual;

import static org.junit.jupiter.api.Assertions.*;

class SHCUserAutoModeRuleTest {

    SHCUserAutoModeRule SHCAutoTest = new SHCUserAutoModeRule();

    @Test
    void validateTest() {
        Individual testIndividual = new Individual(5, "TestName", "Family Adult", "outside", "TestUserName");
        User.setCurrentIndividual(testIndividual);
        assertTrue(SHCAutoTest.validate(testIndividual.getId()));
        testIndividual.setRole("Stranger");
        assertFalse(SHCAutoTest.validate(testIndividual.getId()));
        testIndividual.setRole("Family Child");
        testIndividual.setLocation("not outside");
        assertTrue(SHCAutoTest.validate(testIndividual.getId()));
        testIndividual.setRole("Guest");
        assertTrue(SHCAutoTest.validate(testIndividual.getId()));
        testIndividual.setLocation("outside");
        assertFalse(SHCAutoTest.validate(testIndividual.getId()));
    }
}