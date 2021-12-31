package fr.lernejo.travelsite.service;

import fr.lernejo.travelsite.controllers.dto.InscriptionDto;
import fr.lernejo.travelsite.controllers.dto.PredictionResponse;
import fr.lernejo.travelsite.controllers.dto.TemperatureResponse;
import fr.lernejo.travelsite.controllers.dto.TravelResponse;
import fr.lernejo.travelsite.required.PredictionEngineClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class PredictionService {

    private final PredictionEngineClient predictionEngineClient;
    private final List<InscriptionDto> inscriptionDtos = new ArrayList<>();
    private final List<TravelResponse> travelResponses = new ArrayList<>();

    public PredictionService(PredictionEngineClient predictionEngineClient) {
        this.predictionEngineClient = predictionEngineClient;

    }

    private Stream<String> getCountries() {
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

    public List<PredictionResponse> getPredictions() {
        List<PredictionResponse> predictionResponses = new ArrayList<>();
        Stream<String> lines = getCountries();
        if (lines == null) return predictionResponses;
        lines.forEach(country -> predictionResponses.add(prediction(country)));
        return predictionResponses;
    }

    public List<TravelResponse> predictionMethod(String userName) {
        Map<String, Double> countryAverages = new HashMap<>();
        InscriptionDto inscriptionDto = getInscription(userName);
        if (inscriptionDto == null) return new ArrayList<>();
        else {
            getPredictions().
                forEach(el -> countryAverages.put(el.getCountry(), el.getTemperatures().stream()
                    .mapToDouble(TemperatureResponse::getTemperature).average().orElseGet(() -> 0d)));
            if (inscriptionDto.weatherExpectation().equals("COLDER"))
                return resultColder(countryAverages, inscriptionDto.minimumTemperatureDistance());
            return inscriptionDto.weatherExpectation().equals("WARMER") ?
                resultWarmer(countryAverages, inscriptionDto.minimumTemperatureDistance()) : new ArrayList<>();
        }
    }

    private InscriptionDto getInscription(String userName) {
        List<InscriptionDto> result = inscriptionDtos.stream().filter(el -> el.userName().equals(userName)).toList();
        if (result.isEmpty()) return null;
        return result.get(0);
    }

    private double roundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.parseDouble(df2.format(val));
    }

    private List<TravelResponse> resultColder(Map<String, Double> countryAverages, Integer minimumTemperatureDistance) {
        countryAverages.entrySet().stream().filter(el -> el.getValue() < minimumTemperatureDistance)
            .forEach(el -> travelResponses.add(new TravelResponse(el.getKey(), roundTo2Decimals(el.getValue()))));
        return travelResponses;
    }

    private List<TravelResponse> resultWarmer(Map<String, Double> countryAverages, Integer minimumTemperatureDistance) {
        countryAverages.entrySet().stream().filter(el -> el.getValue() > minimumTemperatureDistance)
            .forEach(el -> travelResponses.add(new TravelResponse(el.getKey(), roundTo2Decimals(el.getValue()))));
        return travelResponses;
    }

    public PredictionResponse prediction(String country) {
        PredictionResponse predictionResponse = null;
        try {
            predictionResponse = predictionEngineClient.prediction(country).clone().execute().body();
            return predictionResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PredictionResponse(country, new ArrayList<>());
    }

    public void inscription(InscriptionDto inscriptionDto) {
        if (!inscriptionDtos.stream().filter(el -> el.userName().equals(inscriptionDto.userName())).toList().isEmpty())
            return;
        inscriptionDtos.add(inscriptionDto);
    }

}
