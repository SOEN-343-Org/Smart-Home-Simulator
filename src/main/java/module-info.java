module org.soen343 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens org.soen343 to javafx.fxml;
    exports org.soen343;
    opens org.soen343.controller to javafx.fxml;
}