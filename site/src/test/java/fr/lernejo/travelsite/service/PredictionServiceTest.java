package fr.lernejo.travelsite.service;

import fr.lernejo.travelsite.controllers.dto.InscriptionDto;
import fr.lernejo.travelsite.controllers.dto.PredictionResponse;
import fr.lernejo.travelsite.controllers.dto.TemperatureResponse;
import fr.lernejo.travelsite.required.PredictionEngineClient;
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
    private InscriptionDto inscriptionDto;

    List<TemperatureResponse> temperatureResponseList =
        List.of(new TemperatureResponse("2021-01-01", 15), new TemperatureResponse("2021-01-02", 15));
    PredictionResponse predictionResponse = new PredictionResponse("France", temperatureResponseList);

    @Test
    void prediction_simple_test() {
        predictionService.inscription(inscriptionDto);
        Mockito.when(predictionEngineClient.prediction(Mockito.anyString())).thenReturn(Calls.response(predictionResponse));
        PredictionResponse predictionResponse = predictionService.prediction("France");
        boolean actualCountry = predictionResponse.getCountry().equals("France");
        boolean actualTempSize = predictionResponse.getTemperatures().size() > 1;
        Assertions.assertTrue(actualCountry);
        Assertions.assertTrue(actualTempSize);
    }

//    @Test
//    void prediction_complete_test() {
//        predictionService.inscription(inscriptionDto);
//        Mockito.when(predictionEngineClient.prediction(Mockito.anyString())).thenReturn(Calls.response(predictionResponse));
//        Mockito.when(predictionService.getPredictions()).thenReturn(predictionResponses);
//        List<TravelResponse> travelResponses = predictionService.predictionMethod(inscriptionDto.userName());
//        boolean actual = travelResponses.get(0).temperature() < 10;
//        Assertions.assertTrue(actual);
//    }

    @BeforeEach
    void setup() {
        predictionService = new PredictionService(predictionEngineClient);
        inscriptionDto = new InscriptionDto("yassine@gmail.com", "yassine", "France", "COLDER", 10);
    }
}
