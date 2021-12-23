package fr.lernejo.prediction.exception;

public class UnknownCountryException extends RuntimeException {
    public UnknownCountryException(String country) {
        super("Unknown country: " + country);
    }
}
