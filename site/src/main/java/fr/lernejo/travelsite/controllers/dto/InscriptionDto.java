package fr.lernejo.travelsite.controllers.dto;

public record InscriptionDto(String userEmail,
                             String userName,
                             String userCountry,
                             String weatherExpectation,
                             Integer minimumTemperatureDistance) {
}
