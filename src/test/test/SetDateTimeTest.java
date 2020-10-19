package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.soen343.models.Model;

import java.time.LocalDate;

public class SetDateTimeTest {

    public static Model model;

    @BeforeAll
    public static void setup(){
        model= new Model();
    }

    @Test
    public void setDate_whenExpectedIsEqualToActual_returnTrue(){
        model.dateTime.setDate(LocalDate.now());
        Assertions.assertEquals(LocalDate.now(), model.dateTime.getDate());
    }

    @Test
    public void setDate_checkIfNotNull_returnTrue(){
        model.dateTime.setDate(LocalDate.now().plusDays(3));
        Assertions.assertNotNull(model.dateTime.getDate());
    }

    @Test
    public void dateNotSet_checkIfNull_returnTrue(){
        Assertions.assertNull(model.dateTime.getDate());
    }
}
