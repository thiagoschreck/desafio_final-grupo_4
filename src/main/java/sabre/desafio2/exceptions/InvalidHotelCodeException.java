package sabre.desafio2.exceptions;

import lombok.Data;

@Data
public class InvalidHotelCodeException extends Exception {
    public final String ERROR = "Could not find any hotel that matches the specified code.";

    public InvalidHotelCodeException() {
        super();
    }
}
