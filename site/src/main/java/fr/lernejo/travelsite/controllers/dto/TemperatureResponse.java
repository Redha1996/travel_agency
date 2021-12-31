package fr.lernejo.travelsite.controllers.dto;

import java.util.StringJoiner;


public class TemperatureResponse {
    private final String date;
    private final double temperature;

    public TemperatureResponse(String date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TemperatureResponse.class.getSimpleName() + "[", "]")
            .add("date='" + date + "'")
            .add("temperature='" + temperature + "'")
            .toString();
    }
}
