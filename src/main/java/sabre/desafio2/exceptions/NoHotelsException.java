package sabre.desafio2.exceptions;

public class NoHotelsException extends Exception {
    public final String ERROR = "No hay hoteles registrados";

    public NoHotelsException() {
        super();
    }
}
