package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.soen343.models.house.Door;
import org.soen343.models.house.Light;
import org.soen343.models.house.Room;
import org.soen343.models.house.Window;

public class ACTest {
    private Room room;

    @BeforeEach
    public void setup(){
        Light[] lights = new Light[]{new Light(1), new Light(2)};
        Window top= new Window(3);
        Door right= new Door(4);
        Door down= new Door(5);
        Window left= new Window(6);
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
        room.getAC().setOn(false);
        Assertions.assertFalse(room.getAC().isOn());
    }
}
