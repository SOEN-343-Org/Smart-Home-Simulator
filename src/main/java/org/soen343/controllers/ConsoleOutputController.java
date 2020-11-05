package org.soen343.controllers;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.soen343.services.ConsoleOutputService;

public class ConsoleOutputController extends Controller {

    @FXML
    private TextFlow outputArea;

    private ConsoleOutputService consoleOutputService;

    void initializeController() {
        consoleOutputService = ConsoleOutputService.getInstance();

    }

    @Override
    void update() {
        String log = consoleOutputService.getLog() + "\n";
        String level = consoleOutputService.getLevel();
        Text t = new Text(log);
        selectTextColor(level, t);
        outputArea.getChildren().add(t);
    }

    private void selectTextColor(String level, Text t) {
        switch (level) {
            case "INFO":
                t.setFill(Color.BLACK);
                break;
            case "CRITICAL":
                t.setFill(Color.RED);
                break;
            case "ERROR":
                t.setFill(Color.ORANGE);
                break;
        }
    }


}
