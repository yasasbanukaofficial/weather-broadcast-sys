package lk.ijse.weatherbroadcastsys.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    private DataOutputStream dOS;
    private DataInputStream dIS;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void connectServerOnAction(MouseEvent mouseEvent) {
        new Thread(() -> {
            try {
                socket = new Socket(txtServerIp.getText(), Integer.parseInt(txtPort.getText()));
                dIS = new DataInputStream(socket.getInputStream());
                dOS = new DataOutputStream((socket.getOutputStream()));
                Platform.runLater(() -> {
                    lblStatus.setStyle("-fx-text-fill: green");
                    lblStatus.setText("Connected!");
                    btnConnect.setStyle("-fx-background-color: red; -fx-text-fill: white");
                    btnConnect.setText("Disconnect");
                });
            } catch (Exception e) {
                lblStatus.setText("Connection Failed!");
            }
        }).start();
    }
}
