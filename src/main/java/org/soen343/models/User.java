package org.soen343.models;

public class User {
    private String name;
    private Individual currentIndividual;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Individual getCurrentIndividual() {
        return currentIndividual;
    }

    public void setCurrentIndividual(Individual currentIndividual) {
        this.currentIndividual = currentIndividual;
    }
}
