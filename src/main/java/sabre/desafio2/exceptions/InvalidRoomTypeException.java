package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class InvalidRoomTypeException extends Exception {
    public final String ERROR = "The specified room type is not compatible with the amount of people.";

    public InvalidRoomTypeException() {
        super();
    }
}
