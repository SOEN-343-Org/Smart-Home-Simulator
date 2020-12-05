//package test;
//
//import org.junit.jupiter.api.*;
//import org.soen343.models.Model;
//import org.soen343.services.SmartHomeSimulatorModuleService;
//
//import java.time.LocalDate;
//
//public class U4U9Test {
//
//    public static Model model;
//    public static SmartHomeSimulatorModuleService smartHomeSimulatorModuleService;
//
//    @BeforeEach
//    public  void setup(){
//        model= new Model();
//        smartHomeSimulatorModuleService = new SmartHomeSimulatorModuleService();
//    }
//
//    @AfterEach
//    public void teardown(){
//        model=null;
//        smartHomeSimulatorModuleService= null;
//    }
//
//    @Test
//    public void setDate_whenExpectedIsEqualToActual_returnTrue(){
//        model.dateTime.setDate(LocalDate.now());
//        Assertions.assertEquals(LocalDate.now(), model.dateTime.getDate());
//    }
//
//    @Test
//    public void setDate_checkIfNotNull_returnTrue(){
//        model.dateTime.setDate(LocalDate.now().plusDays(3));
//        Assertions.assertNotNull(model.dateTime.getDate());
//    }
//
//    @Test
//    public void dateNotSet_checkIfNull_returnTrue(){
//        Assertions.assertNull(model.dateTime.getDate());
//    }
//
//    @Test
//    public void setTime_checkIfNotNull_returnTrue(){
//        model.dateTime.setHours(3);
//        Assertions.assertNotNull(model.dateTime.getHours());
//    }
//
//    @Test
//    public void setTime_whenExpectedIsEqualToActual_returnTrue(){
//        model.dateTime.setHours(4);
//        model.dateTime.setMinutes(4);
//        model.dateTime.setSeconds(4);
//        Assertions.assertEquals(4,model.dateTime.getHours());
//        Assertions.assertEquals(4, model.dateTime.getMinutes());
//        Assertions.assertEquals(4,model.dateTime.getSeconds());
//    }
//    // Testing with smart home simulator module service
//    @Test
//    public void setDateForSimulator_whenExpectedIsEqualToActual_returnTrue(){
//        smartHomeSimulatorModuleService.updateDateTimeDate(LocalDate.now());
//        Assertions.assertEquals(LocalDate.now(), smartHomeSimulatorModuleService.model.dateTime.getDate());
//    }
//
//    @Test
//    public void noDateSetForSimulator_checkIfNull_returnTrue(){
//        smartHomeSimulatorModuleService.model.dateTime.setDate(null);
//        Assertions.assertNull(smartHomeSimulatorModuleService.model.dateTime.getDate());
//    }
//
//    @Test
//    public void setDateForSimulator_checkIfNotNull_returnTrue(){
//        smartHomeSimulatorModuleService.updateDateTimeDate(LocalDate.now());
//        Assertions.assertNotNull(smartHomeSimulatorModuleService.model.dateTime.getDate());
//    }
//
//    @Test
//    public void setTimeForSimulator_whenExpectedIsEqualToActual_returnTrue(){
//       int hours, minutes,seconds;
//       hours=4;
//       minutes=4;
//       seconds=4;
//       smartHomeSimulatorModuleService.updateTime(hours,minutes,seconds);
//       Assertions.assertEquals(4,smartHomeSimulatorModuleService.model.dateTime.getHours());
//       Assertions.assertEquals(4, smartHomeSimulatorModuleService.model.dateTime.getMinutes());
//       Assertions.assertEquals(4,smartHomeSimulatorModuleService.model.dateTime.getSeconds());
//    }
//}
