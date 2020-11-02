package org.soen343.services;

import org.soen343.controllers.Observer;

public interface Observable {

    Observable attachObserver(Observer o);

    void detachObserver(Observer o);

    void notifyObservers(Observable s);
}
