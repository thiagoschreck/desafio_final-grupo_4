package sabre.desafio2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sabre.desafio2.DTOs.FlightBookingErrorDTO;
import sabre.desafio2.DTOs.HotelBookingErrorDTO;
import sabre.desafio2.DTOs.StatusDTO;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(HotelBookingException.class)
    public ResponseEntity<HotelBookingErrorDTO> parameterException(HotelBookingException exception) {
        StatusDTO status = new StatusDTO(400, exception.getMessage());
        HotelBookingErrorDTO error = new HotelBookingErrorDTO(exception.getBooking(), status);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FlightBookingException.class)
    public ResponseEntity<FlightBookingErrorDTO> parameterException(FlightBookingException exception) {
        StatusDTO status = new StatusDTO(400, exception.getMessage());
        FlightBookingErrorDTO error = new FlightBookingErrorDTO(exception.getBooking(), status);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
