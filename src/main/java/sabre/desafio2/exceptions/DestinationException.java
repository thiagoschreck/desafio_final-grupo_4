package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class DestinationException extends Exception {
    public final String ERROR = "El destino elegido no existe";

    public DestinationException() {
        super();
    }
}
