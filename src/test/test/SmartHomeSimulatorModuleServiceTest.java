package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

import org.soen343.models.House;
import org.soen343.models.Room;
import org.soen343.services.Service;
import org.soen343.services.SmartHomeSimulatorModuleService;
import org.soen343.models.Model;



public class SmartHomeSimulatorModuleServiceTest extends Service {

    private static SmartHomeSimulatorModuleService smartHomeSimulatorModuleService;
    private static House house;
    @BeforeAll
    public static void setup(){
        model= new Model();
        smartHomeSimulatorModuleService= new SmartHomeSimulatorModuleService();
    }



    @Test
    public void addedIndividual_returnsTrue(){
        String name, role, location,username;
        name= "test67";
        role= "test2";
        username= "tester";
        location= "kitchen";
        boolean isAdded= model.addIndividual(name, role, username,location);
        Assertions.assertTrue(isAdded);
    }

    // This was given that the next id was going to be 31
    @Test
    public void addedIndividual_thenRemoves_returnsTrue(){
        String name, role, location,username;
        name= "test67";
        role= "test2";
        username= "tester";
        location= "kitchen";
        model.addIndividual(name,role,username,location);
        boolean isRemoved= model.removeIndividual(31);
        Assertions.assertTrue(isRemoved);

    }

}
