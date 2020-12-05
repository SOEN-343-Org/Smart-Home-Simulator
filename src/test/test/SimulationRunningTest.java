package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.soen343.models.Model;
import org.soen343.models.parameters.SimulationParameters;

public class SimulationRunningTest {

   private SimulationParameters simulationParameters;

    @BeforeEach
    public void setup(){
        simulationParameters= new SimulationParameters();
    }

    @Test
    public void checkIfSimulationIsRunningWhenOff(){
        Assertions.assertFalse(simulationParameters.isSimulationRunning());
    }

    @Test
    public void checkIfSimulationIsRunningWhenSetOn(){
        simulationParameters.setSimulationIsRunning();
        Assertions.assertTrue(simulationParameters.isSimulationRunning());
    }
}
