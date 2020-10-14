package org.soen343.models;

/**
 * A Reporter or Subject interface
 */
public interface Reporter {

    public void registerObserver(Observer o);
    public void unregister(Observer o);
    public void notifyObserver();
}
