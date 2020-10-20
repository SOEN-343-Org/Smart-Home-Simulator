package test;

import org.junit.jupiter.api.*;
import org.soen343.services.HouseLayoutService;

public class U1Test {
    public static HouseLayoutService houseLayoutService;

    @BeforeEach
    public  void setup(){
        houseLayoutService= new HouseLayoutService();
    }

    @AfterEach
    public void teardown(){houseLayoutService= null;}

    @Test
    public void retrieveLayout_thenReturnNotNull() {
        Assertions.assertNotNull(houseLayoutService.getHouseLayout());
    }

}
