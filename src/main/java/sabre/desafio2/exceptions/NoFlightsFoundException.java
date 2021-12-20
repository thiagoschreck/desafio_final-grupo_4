package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class NoFlightsFoundException extends Exception {
    public final String ERROR = "Could not find any flight available between the given dates.";

    public NoFlightsFoundException() {
        super();
    }
}
