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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
class PredictionServiceTest {

    @Mock
    private PredictionEngineClient predictionEngineClient;
    private PredictionService predictionService;
    private InscriptionDto inscriptionDtoWarmer;
    private InscriptionDto inscriptionDtoColder;
    private final List<PredictionResponse> predictionResponses = new ArrayList<>();

    List<TemperatureResponse> temperatureResponseList =
        List.of(new TemperatureResponse("2021-01-01", 41.20), new TemperatureResponse("2021-01-02", 34.21));
    PredictionResponse predictionResponse = new PredictionResponse("Austria", temperatureResponseList);

    @Test
    void prediction_simple_test() {
        predictionService.inscription(inscriptionDtoWarmer);
        Mockito.when(predictionEngineClient.prediction(Mockito.anyString())).thenReturn(Calls.response(predictionResponse));
        PredictionResponse predictionResponse = predictionService.prediction("Austria");
        boolean actualCountry = predictionResponse.getCountry().equals("Austria");
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
        Mockito.when(predictionEngineClient.prediction(Mockito.anyString()))
            .thenReturn(Calls.response(predictionResponses.get(15)));
        List<TravelResponse> travelResponses = predictionService.predictionMethod(inscriptionDtoWarmer.userName(), predictionResponses);
        if (travelResponses.isEmpty()) Assertions.assertTrue(true);
        else {
            boolean actual = travelResponses.get(0).temperature() > inscriptionDtoWarmer.minimumTemperatureDistance();
            Assertions.assertTrue(actual);
        }


    }

    @Test
    void prediction_colder_test() {
        predictionService.inscription(inscriptionDtoColder);
        Mockito.when(predictionEngineClient.prediction(Mockito.anyString())).thenReturn(
            Calls.response(predictionResponse));
        List<TravelResponse> travelResponses = predictionService.predictionMethod(inscriptionDtoColder.userName(), predictionResponses);
        if (travelResponses.isEmpty()) Assertions.assertTrue(true);
        else {
            boolean actual = travelResponses.get(0).temperature() < inscriptionDtoColder.minimumTemperatureDistance();
            Assertions.assertTrue(actual);
        }

    }


    @BeforeEach
    void setup() {
        generatePredictionResponse();
        predictionService = new PredictionService(predictionEngineClient, new PredictionUtil());
        inscriptionDtoWarmer = new InscriptionDto("test@gmail.com", "test", "Estonia", "WARMER", 10);
        inscriptionDtoColder = new InscriptionDto("redha@gmail.com", "redha", "France", "COLDER", 20);
    }

    private void generatePredictionResponse() {
        for (int i = 0; i < countries.size(); i++) {
            double random = new Random().nextDouble();
            List<TemperatureResponse> temperatureResponseList =
                List.of(new TemperatureResponse("2021-01-01", (random * (46) + 10)), new TemperatureResponse("2021-01-02", (random * (46) + 10)));
            PredictionResponse predictionResponse = new PredictionResponse(countries.get(i), temperatureResponseList);
            predictionResponses.add(predictionResponse);
        }
    }

    private static final List<String> countries = List.of(
        "Afghanistan",
        "Albania",
        "Algeria",
        "Andorra",
        "Angola",
        "Antigua",
        "Argentina",
        "Armenia",
        "Australia",
        "Austria",
        "Azerbaijan",
        "The Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bhutan",
        "Bolivia",
        "Bosnia",
        "Botswana",
        "Brazil",
        "Brunei",
        "Bulgaria",
        "Burkina Faso",
        "Burundi",
        "Cabo Verde",
        "Cambodia",
        "Cameroon",
        "Canada",
        "Central African Republic",
        "Chad",
        "Chile",
        "China",
        "Colombia",
        "Comoros",
        "Congo",
        "Costa Rica",
        "Côte d’Ivoire",
        "Croatia",
        "Cuba",
        "Cyprus",
        "Czech Republic",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",
        "East Timor",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Eritrea",
        "Estonia",
        "Eswatini",
        "Ethiopia",
        "Fiji",
        "Finland",
        "France",
        "Gabon",
        "The Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Greece",
        "Grenada",
        "Guatemala",
        "Guinea",
        "Guinea-Bissau",
        "Guyana"

    );

}
