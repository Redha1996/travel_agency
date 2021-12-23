package fr.lernejo.prediction.controllers.dto;

import java.util.List;

public record PredictionResponse(
    String country,
    List<TemperatureResponse> temperatures) {
}
