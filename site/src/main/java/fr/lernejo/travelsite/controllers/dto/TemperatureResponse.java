package fr.lernejo.travelsite.controllers.dto;

public class TemperatureResponse {
    private final String date;
    private final double temperature;

    public TemperatureResponse(String date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

}
