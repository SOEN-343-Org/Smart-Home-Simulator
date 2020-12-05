package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.soen343.models.parameters.SmartHeatingParameters;

public class smartHeatingParametersTest {
    private  SmartHeatingParameters smartHeatingParameters;

    @BeforeEach
    public void setup(){
        smartHeatingParameters= new SmartHeatingParameters();
    }

    @Test
    public void checkDefaultSummerTempIfEqualTo20(){
        Assertions.assertEquals(20, smartHeatingParameters.getSummerTemp());
    }

    @Test
    public void checkDefaultWinterTempIfEqualTo25(){
        Assertions.assertEquals(25, smartHeatingParameters.getWinterTemp());
    }

    @Test
    public void setSummerTemp(){
        smartHeatingParameters.setSummerTemp(17);
        Assertions.assertEquals(17, smartHeatingParameters.getSummerTemp());
    }

    @Test
    public void setWinterTemp(){
        smartHeatingParameters.setWinterTemp(22);
        Assertions.assertEquals(22, smartHeatingParameters.getWinterTemp());
    }

    @Test
    public void checksIfSummer(){
        Assertions.assertTrue(smartHeatingParameters.isSummer(9));
    }

    @Test
    public void checksIfWinter(){
        Assertions.assertFalse(smartHeatingParameters.isSummer(1));
    }
}
