//package test;
//
//import org.junit.jupiter.api.*;
//import org.soen343.models.Individual;
//import org.soen343.models.User;
//import org.soen343.services.SmartHomeSimulatorModuleService;
//
//public class U2U6Test {
//    public  SmartHomeSimulatorModuleService smartHomeSimulatorModuleService;
//    public Individual individual;
//
//    @BeforeEach
//    public void setup(){
//        smartHomeSimulatorModuleService= new SmartHomeSimulatorModuleService();
//    }
//
//    @AfterEach
//    public void teardown(){
//        smartHomeSimulatorModuleService= null;
//        individual= null;
//        User.setCurrexntIndividual(individual);
//    }
//
//    @Test
//    public void chooseIndividualAndSetLocation_whenExpectedAndActualIsEqual_returnTrue(){
//        individual= new Individual(5, "test", "tester", "kitchen", "Testing");
//        smartHomeSimulatorModuleService.updateUserIndividual(individual);
//        smartHomeSimulatorModuleService.updateIndividualLocation(individual, "bathroom");
//        Assertions.assertEquals(individual,smartHomeSimulatorModuleService.getCurrentUserIndividual());
//        Assertions.assertEquals("bathroom",smartHomeSimulatorModuleService.getCurrentUserLocation());
//    }
//
//    @Test
//    public void chooseIndividualAndSetLocation_whenBothAreNull_returnTrue(){
//        individual=  null;
//        smartHomeSimulatorModuleService.updateUserIndividual(individual);
//        smartHomeSimulatorModuleService.updateIndividualLocation(individual, null);
//        Assertions.assertNull(smartHomeSimulatorModuleService.getCurrentUserIndividual());
//        Assertions.assertEquals("outside",smartHomeSimulatorModuleService.getCurrentUserLocation());
//
//    }
//
//    @Test
//    public void chooseIndividualButSetLocationAsNull_returnTrue(){
//        individual= new Individual(5, "test", "tester", "kitchen", "Testing");
//        smartHomeSimulatorModuleService.updateUserIndividual(individual);
//        smartHomeSimulatorModuleService.updateIndividualLocation(individual, null);
//        Assertions.assertEquals(individual,smartHomeSimulatorModuleService.getCurrentUserIndividual());
//        Assertions.assertNull(smartHomeSimulatorModuleService.getCurrentUserLocation());
//    }
//
//    @Test
//    public void chooseIndividualAndSetLocation_whenLocationIsNotNullAndIndividualIsNull_returnTrue(){
//        individual= null;
//        smartHomeSimulatorModuleService.updateUserIndividual(individual);
//        smartHomeSimulatorModuleService.updateIndividualLocation(individual, "kitchen");
//        Assertions.assertNull(smartHomeSimulatorModuleService.getCurrentUserIndividual());
//        Assertions.assertEquals("outside",smartHomeSimulatorModuleService.getCurrentUserLocation());
//    }
//
//
//}
