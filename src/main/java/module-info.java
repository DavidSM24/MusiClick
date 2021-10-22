module MusiClick {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;

    opens MusiClick to javafx.fxml;
    exports MusiClick;
}
