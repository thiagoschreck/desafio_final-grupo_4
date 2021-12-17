package sabre.desafio2.exceptions;

public class NoHotelsAvailablesException extends Exception {
    public final String ERROR = "No hay hoteles disponibles con los filtros ingresados";

    public NoHotelsAvailablesException() {
        super();
    }
}
