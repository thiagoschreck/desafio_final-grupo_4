package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class NoFlightsAvailablesException extends Exception {
    public final String ERROR = "No hay hoteles disponibles con los filtros ingresados";

    public NoFlightsAvailablesException() {
        super();
    }
}
