package sabre.desafio2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sabre.desafio2.DTOs.FlightAvailableRequestDTO;
import sabre.desafio2.DTOs.FlightBookingRequestDTO;
import sabre.desafio2.DTOs.FlightBookingResponseDTO;
import sabre.desafio2.DTOs.FlightResponseListDTO;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.services.FlightService;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1")
public class FlightController {
    @Autowired
    FlightService flightService;


    /**
     * GET request to get a list of registered flights that meet given filters.
     *
     * @param dateFrom    (optional) flight departure date, it is used as a filter.
     * @param dateTo      (optional) flight arrival date, it is used as a filter.
     * @param origin      (optional) place of flight departure, it is used as a filter.
     * @param destination (optional) place of flight arrival, it is used as a parameter.
     * @return If no parameters are specified, returns a list of all registered flights,
     * If parameters are specified, returns a list of registered flights that meet this filter.
     */
    @GetMapping("/flights")
    public ResponseEntity<FlightResponseListDTO> getFlights(@RequestParam(required = false) String dateFrom,
                                                            @RequestParam(required = false) String dateTo,
                                                            @RequestParam(required = false) String origin,
                                                            @RequestParam(required = false) String destination)
            throws ParseException, DestinationException, DateFromException, OriginException, NoFlightsAvailablesException, NoFlightsException {
        if (dateFrom == null & dateTo == null & origin == null & destination == null)
            return new ResponseEntity<>(flightService.getFlights(), HttpStatus.OK);
        FlightAvailableRequestDTO data = new FlightAvailableRequestDTO(dateFrom, dateTo, origin, destination);
        return new ResponseEntity<>(flightService.availableFlights(data), HttpStatus.OK);
    }

    /**
     * POST request to make a reservation for a specific flight.
     *
     * @param request payload that contains the information to make the reservation.
     * @return response that contains the input data to make the reservation and the transaction result.
     */
    @PostMapping("/flight-reservation")
    public ResponseEntity<FlightBookingResponseDTO> bookFlight(@RequestBody FlightBookingRequestDTO request)
            throws FlightBookingException, ParseException, PeopleRoomException, DestinationException, DateFromException, OriginException {
        return new ResponseEntity<>(flightService.bookFlight(request), HttpStatus.OK);
    }
}
