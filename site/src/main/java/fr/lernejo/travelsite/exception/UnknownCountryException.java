package fr.lernejo.travelsite.exception;

public class UnknownCountryException extends RuntimeException {
    public UnknownCountryException(String country) {
        super("Unknown country: " + country);
    }
}
