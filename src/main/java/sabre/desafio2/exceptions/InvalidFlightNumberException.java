package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class InvalidFlightNumberException extends Exception {
    public final String ERROR = "Could not find any flight that matches the specified code.";

    public InvalidFlightNumberException() {
        super();
    }
}
