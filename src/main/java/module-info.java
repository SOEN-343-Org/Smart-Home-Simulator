module org.soen343 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.soen343 to javafx.fxml;
    exports org.soen343;
}