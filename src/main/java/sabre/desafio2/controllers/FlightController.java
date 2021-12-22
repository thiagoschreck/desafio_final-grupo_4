package sabre.desafio2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sabre.desafio2.exceptions.InvalidDateRangeException;
import sabre.desafio2.exceptions.NoFlightsException;
import sabre.desafio2.exceptions.NoFlightsFoundException;
import sabre.desafio2.models.dtos.Flight.FlightDTO;
import sabre.desafio2.models.dtos.Flight.FlightRequestDTO;
import sabre.desafio2.models.dtos.Shared.StatusDTO;
import sabre.desafio2.services.FlightService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FlightController {
    @Autowired
    FlightService flightService;

    @PostMapping("/flights/new")
    public ResponseEntity<StatusDTO> createFlight(@RequestBody FlightRequestDTO request) throws InvalidDateRangeException, ParseException {
        return new ResponseEntity<>(flightService.createFlight(request), HttpStatus.OK);
    }

    @PutMapping("/flights/edit")
    public ResponseEntity<StatusDTO> updateFlight(@RequestParam String flightNumber, @RequestBody FlightRequestDTO request) throws Exception, InvalidDateRangeException {
        return new ResponseEntity<>(flightService.updateFlight(flightNumber, request), HttpStatus.OK);
    }

    @DeleteMapping("/flights/delete")
    public ResponseEntity<StatusDTO> deleteFlight(@RequestParam String flightNumber) throws NoFlightsException {
        return new ResponseEntity<>(flightService.deleteFlight(flightNumber), HttpStatus.OK);
    }

    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> getFlights(@RequestParam(required = false) String dateFrom,
                                                      @RequestParam(required = false) String dateTo,
                                                      @RequestParam(required = false) String origin,
                                                      @RequestParam(required = false) String destination)
    throws NoFlightsException, NoFlightsFoundException, ParseException, InvalidDateRangeException {
        if (dateFrom == null & dateTo == null & origin == null & destination == null)
            return new ResponseEntity<>(flightService.getFlights(), HttpStatus.OK);
        return new ResponseEntity<>(flightService.availableFlights(dateFrom, dateTo, origin, destination), HttpStatus.OK);
    }

    /*
    @PostMapping("/flight-reservation/new")
    public ResponseEntity<StatusDTO> createReservation(@RequestBody FlightBookingRequestDTO request)
    throws ParseException, InvalidOriginException, InvalidDestinationException, InvalidDateRangeException {
        return new ResponseEntity<>(flightService.createReservation(request), HttpStatus.OK);
    }

    // MODIFICACIONES


    @PutMapping("/flight-reservation/edit")
    public ResponseEntity<StatusDTO> updateReservation(@RequestParam String id, @RequestBody FlightBookingRequestDTO request) throws Exception {
        return new ResponseEntity<>(flightService.updateReservation(id, request), HttpStatus.OK);
    }

    // CONSULTAS



    @GetMapping("/flight-reservations")
    public ResponseEntity<List<FlightReservationDTO>> getReservations() {
        return new ResponseEntity<>(flightService.getReservations(), HttpStatus.OK);
    }

    // BAJAS




     */
}
