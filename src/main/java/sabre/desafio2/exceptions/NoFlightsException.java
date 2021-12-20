package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class NoFlightsException extends Exception{
    public final String ERROR = "There are no registered flights";

    public NoFlightsException() {
        super();
    }
}
