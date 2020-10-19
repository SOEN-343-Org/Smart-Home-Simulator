package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

import org.soen343.models.Model;
import org.soen343.services.Service;
import org.soen343.services.SmartHomeSimulatorModuleService;


public class AddEditUserTest extends Service {

    public static Model model;

    @BeforeAll
    public static void setup(){ model= new Model();}

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

//    @Test
//    public void addedIndividual_thenUpdateName_returnsTrue(){
//        String name, role, location,username;
//        name= "test67";
//        role= "test2";
//        username= "tester";
//        location= "kitchen";
//        model.addIndividual(name,role,username,location);
//        boolean isRemoved= model.updateIndividualName("NewName", (46));
//        Assertions.assertTrue(isRemoved);
//    }


}
