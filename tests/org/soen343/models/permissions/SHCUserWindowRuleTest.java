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
        Individual testIndividual = new Individual(5, "TestName", "Family Adult", "outside", "TestUserName");
        User.setCurrentIndividual(testIndividual);
        assertTrue(SHCWindowTest.validate(testIndividual.getId()));
        testIndividual.setRole("Stranger");
        assertFalse(SHCWindowTest.validate(testIndividual.getId()));
        testIndividual.setRole("Family Child");
        assertFalse(SHCWindowTest.validate(testIndividual.getId()));
        testIndividual.setLocation("kitchen");
        assertTrue(SHCWindowTest.validate(testIndividual.getId()));
    }
}