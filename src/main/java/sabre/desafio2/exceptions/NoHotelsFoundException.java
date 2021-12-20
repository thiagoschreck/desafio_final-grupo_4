package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class NoHotelsFoundException extends Exception {
    public final String ERROR = "Could not find any hotel available between the given dates.";

    public NoHotelsFoundException() {
        super();
    }
}
