package org.soen343.models;

public interface Reporter {

    public void registerObserver(Observer o);
    public void unregister(Observer o);
    public void notifyObserver();
}
