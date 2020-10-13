module org.soen343 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.sql;

    opens org.soen343 to javafx.fxml;
    exports org.soen343;
    opens org.soen343.controller to javafx.fxml;
    opens org.soen343.models to javafx.base;
}