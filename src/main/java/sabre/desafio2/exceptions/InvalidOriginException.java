package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class InvalidOriginException extends Exception {
    public final String ERROR = "The specified origin could not be found.";

    public InvalidOriginException() {
        super();
    }
}
