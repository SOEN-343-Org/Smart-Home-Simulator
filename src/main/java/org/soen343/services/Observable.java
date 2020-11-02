package org.soen343.services;

import org.soen343.controllers.Observer;

import java.util.ArrayList;

public interface Observable {

    ArrayList<Observer> observers = new ArrayList<>();

    Observable attachObserver(Observer o);

    void detachObserver(Observer o);

    void notifyObservers(Observable s);
}
