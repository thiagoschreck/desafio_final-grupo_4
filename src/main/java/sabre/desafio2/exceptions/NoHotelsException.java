package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class NoHotelsException extends Exception {
    public final String ERROR = "There are no registered hotels";

    public NoHotelsException() {
        super();
    }
}
