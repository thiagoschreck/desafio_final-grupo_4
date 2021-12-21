package sabre.desafio2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sabre.desafio2.models.DTOs.StatusDTO;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    // region Hotel Exceptions
    @ExceptionHandler(NoHotelsFoundException.class)
    public ResponseEntity<StatusDTO> NoHotelsFound(NoHotelsFoundException exception) {
        return new ResponseEntity<>(new StatusDTO(exception.getERROR()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidHotelCodeException.class)
    public ResponseEntity<StatusDTO> InvalidHotelCode(InvalidHotelCodeException exception) {
        return new ResponseEntity<>(new StatusDTO(exception.getERROR()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHotelsException.class)
    public ResponseEntity<StatusDTO> NoHotels(NoHotelsException exception) {
        return new ResponseEntity<>(new StatusDTO(exception.getERROR()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRoomTypeException.class)
    public ResponseEntity<StatusDTO> InvalidRoomType(InvalidRoomTypeException exception) {
        return new ResponseEntity<>(new StatusDTO(exception.getERROR()), HttpStatus.NOT_FOUND);
    }
    // endregion

    // region Flight Exceptions
    @ExceptionHandler(NoFlightsFoundException.class)
    public ResponseEntity<StatusDTO> NoFlightsFound(NoFlightsFoundException exception) {
        return new ResponseEntity<>(new StatusDTO(exception.getERROR()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFlightNumberException.class)
    public ResponseEntity<StatusDTO> InvalidFlightNumber(InvalidFlightNumberException exception) {
        return new ResponseEntity<>(new StatusDTO(exception.getERROR()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoFlightsException.class)
    public ResponseEntity<StatusDTO> NoFlights(NoFlightsException exception) {
        return new ResponseEntity<>(new StatusDTO(exception.getERROR()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDestinationException.class)
    public ResponseEntity<StatusDTO> InvalidDestination(InvalidDestinationException exception) {
        return new ResponseEntity<>(new StatusDTO(exception.getERROR()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOriginException.class)
    public ResponseEntity<StatusDTO> InvalidOrigin(InvalidOriginException exception) {
        return new ResponseEntity<>(new StatusDTO(exception.getERROR()), HttpStatus.NOT_FOUND);
    }
    // endregion

    // region Shared
    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<StatusDTO> InvalidDateRange(InvalidDateRangeException exception) {
        return new ResponseEntity<>(new StatusDTO(exception.getERROR()), HttpStatus.BAD_REQUEST);
    }
    // endregion
}
