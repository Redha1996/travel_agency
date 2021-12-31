package fr.lernejo.travelsite.controllers;

import fr.lernejo.travelsite.controllers.dto.InscriptionDto;
import fr.lernejo.travelsite.controllers.dto.TravelResponse;
import fr.lernejo.travelsite.service.PredictionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }


    @PostMapping("/inscription")
    @ResponseStatus(HttpStatus.CREATED)
    public void inscription(@RequestBody InscriptionDto inscriptionDto) {
        predictionService.inscription(inscriptionDto);
    }

    @GetMapping("/travels")
    public List<TravelResponse> prediction(@RequestParam String userName) {
        return predictionService.predictionMethod(userName);
    }

}
