package org.soen343.services.modules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.soen343.models.house.Light;
import org.soen343.models.house.Zone;
import org.soen343.models.house.Room;


import static org.junit.jupiter.api.Assertions.*;

class SHHModuleTest {

    private Zone zone;

    @Test
    public void createZoneAndCheckIfNotNull(){
        zone= new Zone("zone1");
        Assertions.assertNotNull(zone);
    }

    @Test
    public void doNotCreateZoneAndCheckIfNull(){
        Assertions.assertNull(zone);
    }

    @Test
    public void addRoomInZoneCheckIfZoneContainsRoom(){
        zone= new Zone("zone1");
        Light[] lights= new Light[2];
        lights[0]= new Light(1);
        lights[1]= new Light(2);
        Object top= new Object();
        Object right= new Object();
        Object down= new Object();
        Object left= new Object();
        Room room= new Room(4, "Kitchen",lights, top, right, down, left);
        zone.addRoom(room);
        Assertions.assertTrue(zone.getRooms().contains(room));
    }
}