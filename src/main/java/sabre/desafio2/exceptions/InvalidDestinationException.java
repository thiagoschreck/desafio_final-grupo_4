package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class InvalidDestinationException extends Exception {
    public final String ERROR = "The specified destination could not be found.";

    public InvalidDestinationException() {
        super();
    }
}
