package org.soen343.services.modules;

import org.soen343.services.Service;

public class SHPModule extends Service {

    private static SHPModule shpModule = null;

    public static SHPModule getInstance() {
        if (shpModule == null) {
            shpModule = new SHPModule();
        }
        return shpModule;
    }

}
