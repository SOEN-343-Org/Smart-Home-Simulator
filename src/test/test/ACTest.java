package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.soen343.models.house.Light;
import org.soen343.models.house.Room;

public class ACTest {
    private Room room;

    @BeforeEach
    public void setup(){
        Light[] lights = new Light[]{new Light(1), new Light(2)};
        Object top = new Object();
        Object right = new Object();
        Object down = new Object();
        Object left = new Object();
        room = new Room(4, "Kitchen", lights, top, right, down, left);
    }

    @Test
    public void checkIfRoomHasACNotNull(){
        Assertions.assertNotNull(room.getAC());
    }

    @Test
    public void checkIfRoomsACIsOff(){
        Assertions.assertFalse(room.getAC().isOn());
    }

    @Test
    public void turnOnRoomACAndCheckIfOn(){
        room.getAC().setOn(true);
        Assertions.assertTrue(room.getAC().isOn());
    }

    @Test
    public void turnOffACAndCheckIfOff(){
        room.getHeater().setOn(false);
        Assertions.assertFalse(room.getAC().isOn());
    }
}
