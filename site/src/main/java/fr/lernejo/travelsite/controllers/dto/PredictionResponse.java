package fr.lernejo.travelsite.controllers.dto;

import java.util.List;
import java.util.StringJoiner;

public class PredictionResponse {
    private final String country;
    private final List<TemperatureResponse> temperatures;

    public PredictionResponse(String country, List<TemperatureResponse> temperatures) {
        this.country = country;
        this.temperatures = temperatures;
    }



    public String getCountry() {
        return country;
    }

    public List<TemperatureResponse> getTemperatures() {
        return temperatures;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", PredictionResponse.class.getSimpleName() + "[", "]")
            .add("country='" + country + "'")
            .add("temperatures=" + temperatures)
            .toString();
    }
}
