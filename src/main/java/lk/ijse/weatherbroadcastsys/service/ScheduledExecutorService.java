package lk.ijse.weatherbroadcastsys.service;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import lk.ijse.weatherbroadcastsys.config.Config;
import lk.ijse.weatherbroadcastsys.dto.WeatherDTO;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ScheduledExecutorService {
    private OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient(Config.getAPI());
    private WeatherDTO weatherDTO = new WeatherDTO();

    public void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(3000)) {
                System.out.println("Server started on port 3000");
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected");
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    new Thread(() -> handleClient(clientSocket, oos)).start();
                }
            } catch (Exception e) {
                System.out.println("Server Error when starting");
            }
        }).start();
    }

    private void handleClient(Socket clientSocket, ObjectOutputStream oos) {
        try {
            while (clientSocket != null && !clientSocket.isClosed()) {
                updateWeather();
                oos.writeObject(weatherDTO);
                oos.flush();
                Thread.sleep(10_000);
            }
        } catch (Exception e) {
            System.out.println("Error sending weather data to client");
            throw new RuntimeException(e);
        }
    }

    private void updateWeather() {
        try {
            String weatherJSON = openWeatherMapClient.currentWeather().single().byCityName("Colombo").language(Language.ENGLISH).unitSystem(UnitSystem.METRIC).retrieve().asJSON();
            JSONObject jsonObject = new JSONObject(weatherJSON);
            weatherDTO.setTemperature(Double.toString(jsonObject.getJSONObject("main").getDouble("temp")));
            weatherDTO.setHumidity((Double.toString(jsonObject.getJSONObject("main").getDouble("humidity"))));
            weatherDTO.setWindSpeed((Double.toString(jsonObject.getJSONObject("wind").getDouble("speed") * 3.6)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
