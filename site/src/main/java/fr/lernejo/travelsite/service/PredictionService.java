package fr.lernejo.travelsite.service;

import fr.lernejo.travelsite.controllers.dto.InscriptionDto;
import fr.lernejo.travelsite.controllers.dto.PredictionResponse;
import fr.lernejo.travelsite.controllers.dto.TemperatureResponse;
import fr.lernejo.travelsite.controllers.dto.TravelResponse;
import fr.lernejo.travelsite.required.PredictionEngineClient;
import fr.lernejo.travelsite.utils.PredictionUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class PredictionService {

    private final PredictionEngineClient predictionEngineClient;
    private final PredictionUtil predictionUtil;
    private final List<InscriptionDto> inscriptionDtos = new ArrayList<>();

    public PredictionService(PredictionEngineClient predictionEngineClient, PredictionUtil predictionUtil) {
        this.predictionEngineClient = predictionEngineClient;
        this.predictionUtil = predictionUtil;
    }

    public List<PredictionResponse> getPredictions() {
        List<PredictionResponse> predictionResponses = new ArrayList<>();
        Stream<String> lines = predictionUtil.getCountries();
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
                return predictionUtil.resultColder(countryAverages, inscriptionDto.minimumTemperatureDistance());
            return inscriptionDto.weatherExpectation().equals("WARMER") ?
                predictionUtil.resultWarmer(countryAverages, inscriptionDto.minimumTemperatureDistance()) : new ArrayList<>();
        }
    }

    private InscriptionDto getInscription(String userName) {
        List<InscriptionDto> result = inscriptionDtos.stream().filter(el -> el.userName().equals(userName)).toList();
        if (result.isEmpty()) return null;
        return result.get(0);
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
