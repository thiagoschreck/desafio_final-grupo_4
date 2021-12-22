package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class FlightNumberAlreadyExistsException extends Exception {
    public final String ERROR = "The specified flight number is already registered.";

    public FlightNumberAlreadyExistsException() {
        super();
    }
}


