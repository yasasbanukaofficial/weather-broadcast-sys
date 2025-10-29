module lk.ijse.weatherbroadcastsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires openweathermap.api;


    opens lk.ijse.weatherbroadcastsys.controller to javafx.fxml;
    exports lk.ijse.weatherbroadcastsys;
}