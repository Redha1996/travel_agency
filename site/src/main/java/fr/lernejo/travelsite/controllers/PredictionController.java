package fr.lernejo.travelsite.controllers;

import fr.lernejo.travelsite.controllers.dto.InscriptionDto;
import fr.lernejo.travelsite.controllers.dto.TravelResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PredictionController {

    private final List<InscriptionDto> inscriptionDtos = new ArrayList<>();

    @PostMapping("/inscription")
    public void inscription(@RequestBody InscriptionDto inscriptionDto) {
        inscriptionDtos.add(inscriptionDto);
    }

    @GetMapping("/travels")
    public List<TravelResponse> inscription(@RequestParam String userName) {
        return List.of(new TravelResponse("MA", 44), new TravelResponse("FR", 15));
    }
}
