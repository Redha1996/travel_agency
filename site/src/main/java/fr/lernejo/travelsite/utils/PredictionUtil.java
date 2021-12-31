package fr.lernejo.travelsite.utils;

import fr.lernejo.travelsite.controllers.dto.TravelResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class PredictionUtil {
    private final List<TravelResponse> travelResponses = new ArrayList<>();

    public Stream<String> getCountries() {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("countries.txt");
            String content = null;
            assert inputStream != null;
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return content.lines();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double roundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.parseDouble(df2.format(val));
    }

    public List<TravelResponse> resultColder(Map<String, Double> countryAverages,
                                             Integer minimumTemperatureDistance, double homeTemperature, String userCountry) {
        double diff = homeTemperature - minimumTemperatureDistance;
        countryAverages.entrySet().stream().filter(el -> !el.getKey().equals(userCountry) && el.getValue() <= diff)
            .forEach(el -> travelResponses.add(new TravelResponse(el.getKey(), roundTo2Decimals(el.getValue()))));
        return travelResponses;
    }

    public List<TravelResponse> resultWarmer(Map<String, Double> countryAverages, Integer minimumTemperatureDistance,
                                             double homeTemperature, String userCountry) {
        double diff = homeTemperature + minimumTemperatureDistance;
        countryAverages.entrySet().stream().filter(el -> !el.getKey().equals(userCountry) && el.getValue() >= diff)
            .forEach(el -> travelResponses.add(new TravelResponse(el.getKey(), roundTo2Decimals(el.getValue()))));
        return travelResponses;
    }

}
