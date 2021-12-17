package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class OriginException extends Exception {
    public final String ERROR = "El origen elegido no existe";

    public OriginException() {
        super();
    }
}
