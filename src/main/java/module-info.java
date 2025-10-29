module lk.ijse.weatherbroadcastsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens lk.ijse.weatherbroadcastsys.controller to javafx.fxml;
    exports lk.ijse.weatherbroadcastsys;
}