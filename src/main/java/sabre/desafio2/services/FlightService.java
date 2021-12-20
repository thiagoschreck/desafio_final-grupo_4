package sabre.desafio2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sabre.desafio2.DTOs.*;
import sabre.desafio2.entities.Flight;
import sabre.desafio2.entities.FlightReservation;
import sabre.desafio2.entities.Reservation;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.repositories.FlightRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FlightService {
    @Autowired
    FlightRepository flightRepository = new FlightRepository();

    // ALTAS

    public StatusDTO createFlight(FlightDTO flight) {
        // Flight newFlight = dtoToFlight(flight);
        // todo - add flight to db
        return new StatusDTO("Vuelo dado de alta correctamente");
    }

    public StatusDTO createReservation(FlightBookingRequestDTO request)
    throws FlightBookingException, ParseException, DestinationException, DateFromException, OriginException {
        checkDatesAndPlaces(new FlightAvailableRequestDTO(request.getFlightReservation().getGoingDate(),
                                                          request.getFlightReservation().getReturnDate(),
                                                          request.getFlightReservation().getOrigin(),
                                                          request.getFlightReservation().getDestination()));
        // todo - add reservation to db
        return new StatusDTO("Reserva de vuelo dada de alta correctamente");
    }

    // MODIFICACIONES

    public StatusDTO updateFlight(String flightNumber, FlightDTO flightDTO) throws Exception {
        // todo - invalid flightNumber exception
        // throw new Exception();
        // Flight flight = dtoToFlight(flightDTO);
        // todo - update flight to db
        return new StatusDTO("Vuelo modificado correctamente");
    }

    public StatusDTO updateReservation(String id, FlightBookingRequestDTO request) throws Exception {
        // todo - invalid id exception
        // throw new Exception();
        // FlightReservation reservation = dtoToFlightReservation(request);
        // todo - update reservation to db
        return new StatusDTO("Reserva de vuelo modificada correctamente");
    }

    // CONSULTAS

    public List<FlightDTO> getFlights() throws NoFlightsException {
        // todo - get list of Flights
        List<FlightDTO> flights = new ArrayList<>();
        // todo - convert flights into DTOs
        return flights;
    }

    public List<FlightDTO> availableFlights(FlightAvailableRequestDTO request)
    throws ParseException, DestinationException, DateFromException, OriginException, NoFlightsAvailablesException {
        checkDatesAndPlaces(request);
        // todo - get list of Flights
        // todo - filter
        List<FlightDTO> flights = new ArrayList<>();
        // todo - convert flights into DTOs
        return flights;
    }

    public List<FlightReservationDTO> getReservations() {
        // todo - get list of Reservations
        // todo - filter
        List<FlightReservationDTO> reservations = new ArrayList<>();
        // todo - convert reservations into DTOs
        return reservations;
    }

    // BAJAS

    public StatusDTO deleteFlight(String flightNumber) {
        // todo - check existence
        // todo - delete flight from db
        return new StatusDTO("Vuelo dado de baja correctamente");
    }

    public StatusDTO deleteReservation(String id) {
        // todo - check existence
        // todo - delete reservation from db
        return new StatusDTO("Reserva de vuelo dada de baja correctamente");
    }

    // AUX FUNCTIONS

    public FlightDTO flightToDTO(Flight flight) {
        return new FlightDTO(flight.getFlightNumber(),
                flight.getName(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getGoingDate(),
                flight.getReturnDate(),
                flight.getSeatType(),
                flight.getFlightPrice());
    }

    public Flight dtoToFlight(FlightDTO flight) {
        // todo - reservations ??
        Set<Reservation> reservations = new HashSet<>();
        return new Flight(flight.getFlightNumber(),
                flight.getName(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getGoingDate(),
                flight.getReturnDate(),
                flight.getSeatType(),
                flight.getFlightPrice(),
                reservations);
    }

    public FlightReservation dtoToFlightReservation(FlightBookingRequestDTO request) throws ParseException {
        FlightReservationDTO reservationDTO = request.getFlightReservation();
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationDTO.getReservationId());
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(reservationDTO.getGoingDate());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(reservationDTO.getReturnDate());
        reservation.setGoingDate(dateF);
        reservation.setReturnDate(dateT);
        reservation.setOrigin(reservationDTO.getOrigin());
        reservation.setDestination(reservationDTO.getDestination());
        // todo

        return new FlightReservation(reservation, request.getUserName());
    }

    public void checkDatesAndPlaces(FlightAvailableRequestDTO request) throws DateFromException, DestinationException, ParseException, OriginException {
        // date validation
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateFrom());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateTo());
        if (dateF.after(dateT))
            throw new DateFromException();
        // origin and destination validation
        boolean existOrigin = false;
        boolean existDestination = false;
        for (Flight flight : flightRepository.getFlights()) {
            if (flight.getOrigin().toUpperCase(Locale.ROOT).equals(request.getOrigin().toUpperCase(Locale.ROOT)))
                existOrigin = true;
            if (flight.getDestination().toUpperCase(Locale.ROOT).equals(request.getDestination().toUpperCase(Locale.ROOT)))
                existDestination = true;
            if (existOrigin && existDestination)
                break;
        }
        if (!existOrigin)
            throw new OriginException();
        if (!existDestination)
            throw new DestinationException();
    }
}
