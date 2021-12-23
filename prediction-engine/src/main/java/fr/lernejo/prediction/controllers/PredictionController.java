package fr.lernejo.prediction.controllers;

import fr.lernejo.prediction.controllers.dto.PredictionResponse;
import fr.lernejo.prediction.controllers.dto.TemperatureResponse;
import fr.lernejo.prediction.service.TemperatureService;
import fr.lernejo.prediction.utils.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RequestMapping("/api/temperature")
@RestController
public class PredictionController {

    private final TemperatureService temperatureService;

    public PredictionController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @GetMapping("")
    public PredictionResponse prediction(@RequestParam String country) {
        double temp1 = temperatureService.getTemperature(country);
        double temp2 = temperatureService.getTemperature(country);
        List<TemperatureResponse> temperatureResponse = List.of(
            new TemperatureResponse(DateUtils.oneDayBefore(new Date()), temp1),
            new TemperatureResponse(DateUtils.twoDaysBefore(new Date()), temp2));
        return new PredictionResponse(country, temperatureResponse);
    }
}
