package lk.ijse.weatherbroadcastsys.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.weatherbroadcastsys.dto.WeatherDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public TextField txtServerIp;
    public TextField txtPort;
    public Button btnConnect;
    public Label lblTemperature;
    public Label lblHumidity;
    public Label lblWindSpeed;
    public LineChart lineChart;
    public Label lblStatus;

    private Socket socket;
    private ObjectInputStream ois;
    private WeatherDTO weatherDTO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            Stage stage = (Stage) lblStatus.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                disconnectServer();
            });
        });
    }

    @FXML
    public void connectServerOnAction(MouseEvent mouseEvent) {
        new Thread(() -> {
            try {
                socket = new Socket(txtServerIp.getText(), Integer.parseInt(txtPort.getText()));
                ois = new ObjectInputStream(socket.getInputStream());
                Platform.runLater(() -> {
                    lblStatus.setStyle("-fx-text-fill: green");
                    lblStatus.setText("Connected!");
                    btnConnect.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 10px");
                    btnConnect.setText("Disconnect");
                    btnConnect.setOnAction(event -> {
                        disconnectServer();
                        Platform.runLater(() -> {
                            lblStatus.setText("Disconnected!");
                            btnConnect.setStyle("-fx-background-color: green; -fx-text-fill: white");
                            btnConnect.setText("Connect");
                        });
                    });
                });
                new Thread(() -> fetchWeatherData(socket, ois)).start();
            } catch (Exception e) {
                lblStatus.setText("Connection Failed!");
            }
        }).start();
    }

    private void fetchWeatherData(Socket clientSocket, ObjectInputStream ois) {
        try {
            while (clientSocket != null && !this.socket.isClosed()) {
                weatherDTO = (WeatherDTO) ois.readObject();
                Platform.runLater(() -> {
                    lblTemperature.setText(weatherDTO.getTemperature() + " ℃");
                    lblHumidity.setText(weatherDTO.getHumidity() + " %");
                    lblWindSpeed.setText(weatherDTO.getWindSpeed() + " km/h");
                });
            }
        } catch (Exception e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
        }
    }

    public void disconnectServer() {
        try {
            if (ois != null) {
                ois.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            Platform.exit();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
