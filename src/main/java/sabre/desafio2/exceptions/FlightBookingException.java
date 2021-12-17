package sabre.desafio2.exceptions;

import lombok.Data;
import sabre.desafio2.DTOs.FlightBookingRequestDTO;

@Data
public class FlightBookingException extends Exception {
    private FlightBookingRequestDTO booking;
    private String message;

    public FlightBookingException() {
        super();
    }

    public FlightBookingException(FlightBookingRequestDTO booking, String message) {
        super();
        this.booking = booking;
        this.message = message;
    }
}
