package org.soen343.services;

import org.soen343.models.User;

public class DashboardService extends Service {

    public DashboardService() {

    }

    //TODO: Add login logic to get the good username and this should be done directly after logging in
    public void populateIndividuals() {
        model.setIndividualsFromUser(User.getUsername());
    }

}
