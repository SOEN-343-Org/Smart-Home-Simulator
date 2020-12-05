//package test;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.soen343.models.Model;
//import org.soen343.services.SmartHomeSimulatorModuleService;
//
//public class U10Test {
//    public SmartHomeSimulatorModuleService smartHomeSimulatorModuleService;
//    public Model model;
//
//    @BeforeEach
//    public void setup(){
//        smartHomeSimulatorModuleService= new SmartHomeSimulatorModuleService();
//        model = new Model();
//    }
//
//    @AfterEach
//    public void teardown(){
//        smartHomeSimulatorModuleService= null;
//        model= new Model();
//    }
//
//    @Test
//    public void setTemperature_checkIfActualIsEqualToExpected_returnTrue(){
//        smartHomeSimulatorModuleService.updateOutsideTemp(20);
//        Assertions.assertEquals(20, smartHomeSimulatorModuleService.model.outsideTemp.getTemperature());
//    }
//
//    @Test
//    public void setTemperatureAsNull_returnTrue(){
//        smartHomeSimulatorModuleService.updateOutsideTemp(null);
//        Assertions.assertNull(smartHomeSimulatorModuleService.model.outsideTemp.getTemperature());
//    }
//
//    @Test
//    public void setTemperature_checksIfNotNull_returnTrue(){
//        smartHomeSimulatorModuleService.updateOutsideTemp(30);
//        Assertions.assertNotNull(smartHomeSimulatorModuleService.model.outsideTemp.getTemperature());
//    }
//}
