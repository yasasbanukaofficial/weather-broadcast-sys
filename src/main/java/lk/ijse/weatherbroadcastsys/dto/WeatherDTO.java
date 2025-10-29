package lk.ijse.weatherbroadcastsys.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WeatherDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String temperature;
    private String humidity;
    private String windSpeed;
    private String weather;
}
