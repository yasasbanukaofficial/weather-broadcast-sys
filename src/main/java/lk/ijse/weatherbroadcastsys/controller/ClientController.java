package lk.ijse.weatherbroadcastsys.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lk.ijse.weatherbroadcastsys.dto.WeatherDTO;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientController {
    public TextField txtServerIp;
    public TextField txtPort;
    public Button btnConnect;
    public Label lblTemperature;
    public Label lblHumidity;
    public Label lblWindSpeed;
    public LineChart lineChart;
    public Label lblStatus;

    private Socket socket;
    private WeatherDTO weatherDTO;

    @FXML
    public void connectServerOnAction(MouseEvent mouseEvent) {
        new Thread(() -> {
            try {
                socket = new Socket(txtServerIp.getText(), Integer.parseInt(txtPort.getText()));
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Platform.runLater(() -> {
                    lblStatus.setStyle("-fx-text-fill: green");
                    lblStatus.setText("Connected!");
                    btnConnect.setStyle("-fx-background-color: red; -fx-text-fill: white");
                    btnConnect.setText("Disconnect");
                });
                new Thread(() -> fetchWeatherData(socket, ois)).start();
            } catch (Exception e) {
                lblStatus.setText("Connection Failed!");
            }
        }).start();
    }

    private void fetchWeatherData(Socket clientSocket, ObjectInputStream ois) {
        try {
            if (clientSocket != null && !this.socket.isClosed()) {
                weatherDTO = (WeatherDTO) ois.readObject();
                System.out.println(weatherDTO.getWindSpeed());
                Platform.runLater(() -> {
                    lblTemperature.setText(weatherDTO.getTemperature() + " ℃");
                    lblHumidity.setText(weatherDTO.getHumidity() + " %");
                    lblWindSpeed.setText(weatherDTO.getWindSpeed() + " km/h");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
