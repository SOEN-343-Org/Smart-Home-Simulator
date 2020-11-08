package org.soen343.models.house;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndividualTest {

    public Individual individual;
    public Individual individual2;

    @BeforeEach
    public void setup(){
        individual= new Individual(1, "test", "adult", "kitchen","test1");
        individual2= new Individual(2,"boo","child","bathroom", "test");
    }

    @AfterEach
    public void teardown(){
        individual= null;
        individual2=null;
    }

    @Test
    public void moveIndividualsToDifferentLocations_returnsTrue(){
        individual.setLocation("washroom");
        individual2.setLocation("basement");
        Assertions.assertEquals("washroom",individual.getLocation());
        Assertions.assertEquals("basement", individual2.getLocation());
    }

    @Test
    public void setLocationsAsNull_returnsTrue(){
        individual.setLocation(null);
        individual2.setLocation(null);
        Assertions.assertNull(individual.getLocation());
        Assertions.assertNull(individual.getLocation());
    }

    @Test
    public void setLocationsCheckIfNotNull_returnFalse(){
        individual.setLocation("bedroom");
        individual.setLocation("bedroom");
        Assertions.assertNotNull(individual.getLocation());
        Assertions.assertNotNull(individual2.getLocation());
    }


}