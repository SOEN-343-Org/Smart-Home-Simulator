package org.soen343.services;

import org.soen343.models.User;

public class DashboardService extends Service {

    public DashboardService() {

    }

    /**
     * Populate individuals
     */
    public void populateIndividuals() {
        model.setIndividualsFromUser(User.getUsername());
    }
}
