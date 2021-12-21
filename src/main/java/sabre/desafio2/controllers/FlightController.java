package sabre.desafio2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.models.DTOs.*;
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
    public ResponseEntity<StatusDTO> createFlight(@RequestBody FlightDTO request) throws ParseException, InvalidDateRangeException, FlightNumberAlreadyExistsException {
        return new ResponseEntity<>(flightService.
                createFlight(request), HttpStatus.OK);
    }

    // MODIFICACIONES

    @PutMapping("/flights/edit")
    public ResponseEntity<StatusDTO> updateFlight(@RequestParam String flightNumber, @RequestBody FlightDTO request) throws Exception {
        return new ResponseEntity<>(flightService.updateFlight(flightNumber, request), HttpStatus.OK);
    }

    // CONSULTAS

    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> getFlights(@RequestParam(required = false) String dateFrom,
                                                            @RequestParam(required = false) String dateTo,
                                                            @RequestParam(required = false) String origin,
                                                            @RequestParam(required = false) String destination)
    throws ParseException, NoFlightsException, InvalidOriginException, InvalidDestinationException, InvalidDateRangeException {
        if (dateFrom == null & dateTo == null & origin == null & destination == null)
            return new ResponseEntity<>(flightService.getFlights(), HttpStatus.OK);
        FlightAvailableRequestDTO data = new FlightAvailableRequestDTO(dateFrom, dateTo, origin, destination);
        return new ResponseEntity<>(flightService.availableFlights(data), HttpStatus.OK);
    }

    // BAJAS

    @DeleteMapping("/flights/delete")
    public ResponseEntity<StatusDTO> deleteFlight(@RequestParam String flightNumber) throws NoFlightsException {
        return new ResponseEntity<>(flightService.deleteFlight(flightNumber), HttpStatus.OK);
    }

}
