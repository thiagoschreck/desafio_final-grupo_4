package sabre.desafio2.exceptions;

import lombok.Data;
import sabre.desafio2.DTOs.HotelBookingRequestDTO;

@Data
public class HotelBookingException extends Exception {
    private HotelBookingRequestDTO booking;
    private String message;

    public HotelBookingException(HotelBookingRequestDTO booking, String message) {
        super();
        this.booking = booking;
        this.message = message;
    }
}
