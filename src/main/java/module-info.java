module lk.ijse.weatherbroadcastsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires openweathermap.api;
    requires org.json;
    requires static lombok;


    opens lk.ijse.weatherbroadcastsys.controller to javafx.fxml;
    exports lk.ijse.weatherbroadcastsys;
}