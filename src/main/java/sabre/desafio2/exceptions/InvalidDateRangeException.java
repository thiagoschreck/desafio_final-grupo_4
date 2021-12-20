package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class InvalidDateRangeException extends Throwable {
    public final String ERROR = "The specified date range is not valid";

    public InvalidDateRangeException() {
        super();
    }
}
