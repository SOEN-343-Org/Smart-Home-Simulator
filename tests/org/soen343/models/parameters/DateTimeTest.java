package org.soen343.models.parameters;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DateTimeTest {

    DateTime dateTimeTest = new DateTime();

    @Test
    void changeTimeSpeedTest() {
        assertEquals(dateTimeTest.getClockSpeedMultiplier(), 1);
        dateTimeTest.setClockSpeedMultiplier(5);
        assertEquals(dateTimeTest.getClockSpeedMultiplier(), 5);
    }
}