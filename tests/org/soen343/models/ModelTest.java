package org.soen343.models;

import org.junit.jupiter.api.*;

class ModelTest {


    public static Model model;

    @BeforeEach
    public  void setup(){ model= new Model();}

    @AfterEach
    public void teardown(){ model= null;}

    @Tag("old-test")
    @Test
    public void addedIndividual_returnsTrue(){
        String name, role, location,username;
        name= "test647";
        role= "test2";
        username= "tester";
        location= "kitchen";
        boolean isAdded= model.addIndividual(name, role, username,location);
        Assertions.assertTrue(isAdded);
    }

    // This was given that the next id was going to be 31
    @Tag("old-test")
    @Test
    public void addedIndividual_thenRemoves_returnsTrue(){
        String name, role, location,username;
        name= "test647";
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