package org.soen343.controllers;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.soen343.services.ConsoleOutputService;

public class ConsoleOutputController extends Controller {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextFlow outputArea;

    private ConsoleOutputService consoleOutputService;

    /**
     * Initialize controller
     */
    void initializeController() {
        outputArea = new TextFlow();
        outputArea.setStyle("-fx-background-color: #FFFFFF;");
        outputArea.setPrefWidth(scrollPane.getPrefWidth());
        consoleOutputService = ConsoleOutputService.getInstance();
        outputArea.getChildren().addListener(
                (ListChangeListener<Node>) ((change) -> {
                    outputArea.layout();
                    scrollPane.layout();
                    scrollPane.setVvalue(1.0f);
                }));
        scrollPane.setContent(outputArea);
    }

    @Override
    void update() {
        String log = consoleOutputService.getLog() + "\n";
        String level = consoleOutputService.getLevel();
        Text t = new Text(log);
        t.setFont(new Font(14));
        selectTextColor(level, t);
        outputArea.getChildren().add(t);
    }

    /**
     * Select text color
     *
     * @param level
     * @param t
     */
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
