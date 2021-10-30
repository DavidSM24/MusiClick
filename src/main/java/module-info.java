module MusiClick {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;

    opens MusiClick to javafx.fxml;
    exports MusiClick;
}
