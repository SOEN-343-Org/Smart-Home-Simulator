package org.soen343.services;


import org.soen343.controllers.Observer;

public abstract class Service implements Observable {

    @Override
    public Observable attachObserver(Observer o) {
        this.observers.add(o);
        return this;
    }

    @Override
    public void detachObserver(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers(Observable observable) {
        for (Observer o : this.observers) {
            o.update(observable);
        }
    }
}
