module MusiClick {
    requires javafx.controls;
    requires javafx.fxml;

    opens MusiClick to javafx.fxml;
    exports MusiClick;
}
