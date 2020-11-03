module org.soen343 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.sql;

    opens org.soen343 to javafx.fxml;
    opens org.soen343.controllers to javafx.fxml;
    opens org.soen343.models to javafx.base;
    opens org.soen343.models.house to javafx.base;
    opens org.soen343.models.parameters to javafx.base;
    exports org.soen343;

}