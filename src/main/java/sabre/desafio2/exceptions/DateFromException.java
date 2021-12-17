package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class DateFromException extends Throwable {
    public final String ERROR = "La fecha de ida debe ser menor a la de vuelta";

    public DateFromException() {
        super();
    }
}
