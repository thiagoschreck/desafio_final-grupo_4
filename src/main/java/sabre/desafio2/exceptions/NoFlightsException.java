package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class NoFlightsException extends Exception{
    public final String ERROR = "No hay vuelos registrados";

    public NoFlightsException() {
        super();
    }
}
