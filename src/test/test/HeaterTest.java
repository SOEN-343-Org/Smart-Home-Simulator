package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.soen343.models.house.Door;
import org.soen343.models.house.Light;
import org.soen343.models.house.Room;
import org.soen343.models.house.Window;

public class HeaterTest {
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
    public void checkIfRoomHasHeaterNotNull(){
        Assertions.assertNotNull(room.getHeater());
    }

    @Test
    public void checkIfRoomsHeaterIsOff(){
        Assertions.assertFalse(room.getHeater().isOn());
    }

    @Test
    public void turnOnRoomHeaterAndCheckIfOn(){
        room.getHeater().setOn(true);
        Assertions.assertTrue(room.getHeater().isOn());
    }

    @Test
    public void turnOffHeaterAndCheckIfOff(){
        room.getHeater().setOn(false);
        Assertions.assertFalse(room.getHeater().isOn());
    }
}
