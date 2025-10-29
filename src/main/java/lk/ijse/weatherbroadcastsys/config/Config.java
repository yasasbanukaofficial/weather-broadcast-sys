package lk.ijse.weatherbroadcastsys.config;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (FileInputStream fIS = new FileInputStream("config.properties")) {
            props.load(fIS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAPI() {
        return props.getProperty("WEATHER_API");
    }
}
