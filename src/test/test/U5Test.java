//package test;
//
//import org.junit.jupiter.api.*;
//import org.soen343.models.Model;
//import org.soen343.services.SimulationInfoService;
//
//public class U5Test {
//    public static Model model;
//    public static SimulationInfoService simulationInfoService;
//
//    @BeforeEach
//    public  void setup(){
//        model= new Model();
//        model.simulationRunning=false;
//        simulationInfoService= new SimulationInfoService();
//    }
//
//    @AfterEach
//    public void teardown(){
//        model.simulationRunning=false;
//        simulationInfoService=null;
//        model=null;
//    }
//
//    @Test
//    public void checkIfSimulationIsRunningWithModel_returnFalse(){ // Tests Using the Service Class
//       boolean isNotRunning = model.simulationRunning;
//       Assertions.assertFalse(isNotRunning);
//    }
//
//    @Test
//    public  void whenSetToRun_checkIfSimulationIsRunningWithModel_returnTrue(){
//        boolean isRunning= !model.simulationRunning;
//        Assertions.assertTrue(isRunning);
//    }
//
//    @Test
//    public void setSimulationIsRunning_withSimulationInfoService_returnTrue(){ // Test with the simulation info service class
//        simulationInfoService.setSimulationRunning();
//        Assertions.assertTrue(simulationInfoService.SimulationIsRunning());
//        simulationInfoService.setSimulationRunning();
//    }
//
//    @Test
//    public void setSimulationIsNotRunning_withSimulationInfoService_returnFalse(){
//        Assertions.assertFalse(simulationInfoService.SimulationIsRunning());
//    }
//
//}
