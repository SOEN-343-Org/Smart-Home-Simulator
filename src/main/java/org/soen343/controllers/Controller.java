package org.soen343.controllers;

import org.soen343.models.Model;

public abstract class Controller {

    public static Model model;

    public void setModel(Model model) {
        Controller.model = model;
    }
}
