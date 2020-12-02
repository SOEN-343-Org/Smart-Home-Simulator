package org.soen343.drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DrawingNull {
    private static final double WIDTH = 500;
    private static final double HEIGHT = 500;

    /**
     * Draw null layout
     */
    public static void drawNullLayout(GraphicsContext gc) {
        gc.setLineWidth(8.0);
        gc.strokeRect(0, 0, WIDTH, HEIGHT);
        gc.setStroke(Color.BLACK);
        gc.setFont(new Font(20));
        Text text = new Text("HOUSE LAYOUT IS NULL, RESTART THE APPLICATION");
        text.setFont(new Font(20));
        double textSize = text.getBoundsInLocal().getWidth();
        gc.strokeText(text.getText(), (WIDTH - textSize) / 2, HEIGHT / 2.0);
    }
}
