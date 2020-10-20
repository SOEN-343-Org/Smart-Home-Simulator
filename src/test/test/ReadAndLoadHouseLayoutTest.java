package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.soen343.services.HouseLayoutService;

public class ReadAndLoadHouseLayoutTest {
    public static HouseLayoutService houseLayoutService;

    @BeforeAll
    public static void setup(){
        houseLayoutService= new HouseLayoutService();
    }

    @Test
    public void retrieveLayout_thenReturnNotNull() {
        Assertions.assertNotNull(houseLayoutService.getHouseLayout());
    }

}
