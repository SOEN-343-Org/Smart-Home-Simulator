package org.soen343.models.parameters;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class AwayModeParametersTest {

    AwayModeParameters ampTest = new AwayModeParameters();
    final int RANDOM_TIME = 30;

    @Test
    void callPoliceTest(){
        ampTest.setTimeBeforeCallingPoliceAfterBreakIn(RANDOM_TIME);
        assertEquals(ampTest.getTimeBeforeCallingPoliceAfterBreakIn(), RANDOM_TIME);
        ampTest.setDateBeforeCallingPolice(Calendar.getInstance());
        assertEquals(ampTest.getDateForCallingPoliceAfterBreakIn(), Calendar.getInstance().getTime());
    }

}