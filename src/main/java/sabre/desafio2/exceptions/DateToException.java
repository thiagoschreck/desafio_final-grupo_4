package sabre.desafio2.exceptions;

public class DateToException extends Exception {
    public final String ERROR = "La fecha de vuelta debe ser mayor a la de ida";

    public DateToException() {
        super();
    }
}
