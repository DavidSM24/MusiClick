module MusiClick {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;

    opens MusiClick to javafx.fxml;
    exports MusiClick;
}
