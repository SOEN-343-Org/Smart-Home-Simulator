package org.soen343.services;

import org.soen343.models.Room;

public class HouseLayoutService extends Service {
    public HouseLayoutService() {
    }

    public Room[][] getHouseLayout() {
        return model.house.getLayout();
    }
}
