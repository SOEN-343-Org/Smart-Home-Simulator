package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;


import org.soen343.models.Individual;
import org.soen343.services.Service;
import org.soen343.services.SmartHomeSimulatorModuleService;
import org.soen343.models.Model;



public class SmartHomeSimulatorModuleServiceTest extends Service {

    private static SmartHomeSimulatorModuleService smartHomeSimulatorModuleService;

    @BeforeAll
    public static void setup(){
        model= new Model();
        smartHomeSimulatorModuleService= new SmartHomeSimulatorModuleService();
    }



    @Test
    public void addedIndividual_when_returnsTrue(){
        String name, role, location,username;
        name= "test67";
        role= "test2";
        username= "tester";
        location= "kitchen";
        boolean isAdded= model.addIndividual(name, role, username,location);
        Assertions.assertTrue(isAdded);
    }


}
