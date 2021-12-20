package sabre.desafio2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sabre.desafio2.DTOs.*;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.services.FlightService;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FlightController {
    @Autowired
    FlightService flightService;

    // ALTAS

    @PostMapping("/flights/new")
    public ResponseEntity<StatusDTO> createFlight(@RequestBody FlightDTO request) {
        return new ResponseEntity<>(flightService.createFlight(request), HttpStatus.OK);
    }

    @PostMapping("/flight-reservation/new")
    public ResponseEntity<StatusDTO> createReservation(@RequestBody FlightBookingRequestDTO request)
            throws FlightBookingException, ParseException, DestinationException, DateFromException, OriginException {
        return new ResponseEntity<>(flightService.createReservation(request), HttpStatus.OK);
    }

    // MODIFICACIONES

    @PutMapping("/flights/edit")
    public ResponseEntity<StatusDTO> updateFlight(@RequestParam String flightNumber, @RequestBody FlightDTO request) throws Exception {
        return new ResponseEntity<>(flightService.updateFlight(flightNumber, request), HttpStatus.OK);
    }

    @PutMapping("/flight-reservation/edit")
    public ResponseEntity<StatusDTO> updateReservation(@RequestParam String id, @RequestBody FlightBookingRequestDTO request) throws Exception {
        return new ResponseEntity<>(flightService.updateReservation(id, request), HttpStatus.OK);
    }

    // CONSULTAS

    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> getFlights(@RequestParam(required = false) String dateFrom,
                                                            @RequestParam(required = false) String dateTo,
                                                            @RequestParam(required = false) String origin,
                                                            @RequestParam(required = false) String destination)
            throws ParseException, DestinationException, DateFromException, OriginException, NoFlightsAvailablesException, NoFlightsException {
        if (dateFrom == null & dateTo == null & origin == null & destination == null)
            return new ResponseEntity<>(flightService.getFlights(), HttpStatus.OK);
        FlightAvailableRequestDTO data = new FlightAvailableRequestDTO(dateFrom, dateTo, origin, destination);
        return new ResponseEntity<>(flightService.availableFlights(data), HttpStatus.OK);
    }

    @GetMapping("/flight-reservations")
    public ResponseEntity<List<FlightReservationDTO>> getReservations() {
        return new ResponseEntity<>(flightService.getReservations(), HttpStatus.OK);
    }

    // BAJAS

    @DeleteMapping("/flights/delete")
    public ResponseEntity<StatusDTO> deleteFlight(@RequestParam String flightNumber) {
        return new ResponseEntity<>(flightService.deleteFlight(flightNumber), HttpStatus.OK);
    }

    @DeleteMapping("/flight-reservation/delete")
    public ResponseEntity<StatusDTO> deleteReservation(@RequestParam String id) {
        return new ResponseEntity<>(flightService.deleteReservation(id), HttpStatus.OK);
    }
}
