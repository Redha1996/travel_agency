package fr.lernejo.prediction.controllers.dto;

public record TemperatureResponse(
    String date,
    double temperature) {
}
