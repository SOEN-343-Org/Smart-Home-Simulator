package test;

import org.junit.jupiter.api.*;
import org.soen343.models.house.Light;
import org.soen343.models.house.Room;
import org.soen343.models.house.Zone;

public class settingZoneTest {
    private Zone zone;
    private Room room;

    @BeforeEach
    public void setup(){
        zone= new Zone("zone1");
    }

    @Test
    public void createZoneAndCheckIfNotNull(){
        Assertions.assertNotNull(zone);
    }

    @Test
    public void doNotCreateZoneAndCheckIfNull(){
        zone=null;
        Assertions.assertNull(zone);
    }

    @Test
    public void addRoomInZoneCheckIfZoneContainsRoom(){
        zone= new Zone("zone1");
        Light [] lights= new Light[2];
        lights[0]= new Light(1);
        lights[1]= new Light(2);
        Object top= new Object();
        Object right= new Object();
        Object down= new Object();
        Object left= new Object();
        room= new Room(4, "Kitchen",lights, top, right, down, left);
        zone.addRoom(room);
        Assertions.assertTrue(zone.getRooms().contains(room));
    }

    @Test
    public void checkIfZoneHasANightTempWithoutSettingTemp(){
        Assertions.assertEquals( 20, zone.getNightTemp());
    }

    @Test
    public void checkIfZoneHasAMorningTempWithoutSettingTemp(){
        Assertions.assertEquals(20, zone.getMorningTemp());
    }

    @Test
    public void checkIfZoneHasAnAfternoonTempWithoutSettingTemp(){
        Assertions.assertEquals(20, zone.getAfternoonTemp());
    }

    @Test
    public void setMorningTempOfZone(){
        zone.setMorningTemp(25);
        Assertions.assertEquals(25,zone.getMorningTemp());
    }

    @Test
    public void setAfternoonTempOfZone(){
        zone.setAfternoonTemp(25);
        Assertions.assertEquals(25,zone.getAfternoonTemp());
    }

    @Test
    public void setNightTempOfZone(){
        zone.setNightTemp(25);
        Assertions.assertEquals(25,zone.getNightTemp());
    }

    @Test
    public void checkIfTempIsOverwrittenReturnFalse(){
        Assertions.assertFalse(zone.getOverwritten());
    }

    @Test
    public void overwriteTempCheckIfOverwritten(){
        zone.setOverwrittenTemp(32);
        Assertions.assertTrue(zone.getOverwritten());
    }
}
