module lk.ijse.weatherbroadcastsys {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.ijse.weatherbroadcastsys.controller to javafx.fxml;
    exports lk.ijse.weatherbroadcastsys;
}