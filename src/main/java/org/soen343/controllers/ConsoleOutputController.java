package org.soen343.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.soen343.services.ConsoleOutputService;

public class ConsoleOutputController extends Controller{
    @FXML
    private TextArea outputArea;
    static TextArea staticTextArea;
    private ConsoleOutputService consoleOutputService;
    void initializeController() {
        consoleOutputService = ConsoleOutputService.getInstance();
    }

    @Override
    void update() {
        String log = consoleOutputService.getLog();
        System.out.println(log);
    }


}