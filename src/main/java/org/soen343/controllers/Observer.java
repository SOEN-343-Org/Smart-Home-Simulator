package org.soen343.controllers;

import org.soen343.services.Observable;

public interface Observer {
    void update(Observable observable);
}
