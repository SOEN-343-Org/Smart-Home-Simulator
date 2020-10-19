package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.soen343.models.Model;
import org.soen343.services.Service;
import org.soen343.services.SimulationInfoService;

public class StartStopSimulationTest {
    public static Model model;
    public static Service service;
    public static SimulationInfoService simulationInfoService;

    @BeforeAll
    public static void setup(){
        model= new Model();
        simulationInfoService= new SimulationInfoService();
        service= new Service() {
            @Override
            public boolean SimulationIsRunning() {
                return super.SimulationIsRunning();
            }
        };
    }

    @Test
    public void checkIfSimulationIsRunningWithService_returnFalse(){ // Tests Using the Service Class
       boolean isNotRunning = service.SimulationIsRunning();
       Assertions.assertFalse(isNotRunning);
    }

    @Test
    public  void whenSetToRun_checkIfSimulationIsRunningWithService_returnTrue(){
        boolean isRunning= !service.SimulationIsRunning();
        Assertions.assertTrue(isRunning);
    }

    @Test
    public void setSimulationIsRunning_withSimulationInfoService_returnTrue(){ // Test with the simulation info service class
        simulationInfoService.setSimulationRunning();
        Assertions.assertTrue(simulationInfoService.SimulationIsRunning());
    }

    @Test
    public void setSimulationIsNotRunning_withSimulationInfoService_returnFalse(){
        Assertions.assertFalse(simulationInfoService.SimulationIsRunning());
    }

}
