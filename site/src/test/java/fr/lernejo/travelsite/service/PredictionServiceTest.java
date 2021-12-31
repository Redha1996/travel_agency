package fr.lernejo.travelsite.service;

import fr.lernejo.travelsite.controllers.dto.InscriptionDto;
import fr.lernejo.travelsite.controllers.dto.PredictionResponse;
import fr.lernejo.travelsite.controllers.dto.TemperatureResponse;
import fr.lernejo.travelsite.controllers.dto.TravelResponse;
import fr.lernejo.travelsite.required.PredictionEngineClient;
import fr.lernejo.travelsite.utils.PredictionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.mock.Calls;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PredictionServiceTest {

    @Mock
    private PredictionEngineClient predictionEngineClient;
    private PredictionService predictionService;
    private InscriptionDto inscriptionDtoWarmer;
    private InscriptionDto inscriptionDtoColder;

    List<TemperatureResponse> temperatureResponseList =
        List.of(new TemperatureResponse("2021-01-01", 5), new TemperatureResponse("2021-01-02", 20));
    PredictionResponse predictionResponse = new PredictionResponse("France", temperatureResponseList);

    @Test
    void prediction_simple_test() {
        predictionService.inscription(inscriptionDtoWarmer);
        Mockito.when(predictionEngineClient.prediction(Mockito.anyString())).thenReturn(Calls.response(predictionResponse));
        PredictionResponse predictionResponse = predictionService.prediction("France");
        boolean actualCountry = predictionResponse.getCountry().equals("France");
        boolean actualTempSize = predictionResponse.getTemperatures().size() > 1;
        Assertions.assertTrue(actualCountry);
        Assertions.assertTrue(actualTempSize);
    }

    @Test
    void getPredictions() {
        Mockito.when(predictionEngineClient.prediction(Mockito.anyString())).thenReturn(Calls.response(predictionResponse));
        List<PredictionResponse> predictionResponses = predictionService.getPredictions();
        boolean actual = predictionResponses.size() == 72;
        Assertions.assertTrue(actual);
    }

    @Test
    void prediction_warmer_test() {
        predictionService.inscription(inscriptionDtoWarmer);
        Mockito.when(predictionEngineClient.prediction(Mockito.anyString())).thenReturn(Calls.response(predictionResponse));
        List<TravelResponse> travelResponses = predictionService.predictionMethod(inscriptionDtoWarmer.userName());
        System.out.println(travelResponses.size());
        boolean actual = travelResponses.get(0).temperature() > inscriptionDtoWarmer.minimumTemperatureDistance();
        Assertions.assertTrue(actual);
    }

    @Test
    void prediction_colder_test() {
        predictionService.inscription(inscriptionDtoColder);
        Mockito.when(predictionEngineClient.prediction(Mockito.anyString())).thenReturn(Calls.response(predictionResponse));
        List<TravelResponse> travelResponses = predictionService.predictionMethod(inscriptionDtoColder.userName());
        System.out.println(travelResponses.size());
        boolean actual = travelResponses.get(0).temperature() < inscriptionDtoColder.minimumTemperatureDistance();
        Assertions.assertTrue(actual);
    }

    @BeforeEach
    void setup() {
        predictionService = new PredictionService(predictionEngineClient, new PredictionUtil());
        inscriptionDtoWarmer = new InscriptionDto("test@gmail.com", "test", "France", "WARMER", 10);
        inscriptionDtoColder = new InscriptionDto("redha@gmail.com", "redha", "France", "COLDER", 20);
    }
}
