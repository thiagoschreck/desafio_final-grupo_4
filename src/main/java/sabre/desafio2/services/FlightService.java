package sabre.desafio2.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sabre.desafio2.models.DTOs.*;
import sabre.desafio2.models.entities.Flight;
import sabre.desafio2.models.entities.FlightReservation;
import sabre.desafio2.models.entities.Reservation;
import sabre.desafio2.exceptions.InvalidDateRangeException;
import sabre.desafio2.exceptions.InvalidDestinationException;
import sabre.desafio2.exceptions.InvalidOriginException;
import sabre.desafio2.exceptions.NoFlightsException;
import sabre.desafio2.repositories.IFlightRepository;
import sabre.desafio2.repositories.IReservationsRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FlightService {
    @Autowired
    IFlightRepository flightRepository;
    IReservationsRepository reservationsRepository;

    ModelMapper mapper = new ModelMapper();

    public FlightService(IFlightRepository flightRepository, IReservationsRepository reservationsRepository) {
        this.flightRepository = flightRepository;
        this.reservationsRepository = reservationsRepository;
    }

    // ALTAS

    public StatusDTO createFlight(FlightDTO flight) throws InvalidDateRangeException {
        if (flight.getGoingDate().after(flight.getReturnDate()))
            throw new InvalidDateRangeException();
        flightRepository.save(mapper.map(flight, Flight.class));
        return new StatusDTO("Vuelo dado de alta correctamente");
    }

    public StatusDTO createReservation(FlightBookingRequestDTO request)
    throws ParseException, InvalidOriginException, InvalidDestinationException, InvalidDateRangeException {
//        checkDatesAndPlaces(new FlightAvailableRequestDTO(request.getFlightReservation().getGoingDate(),
//                                                          request.getFlightReservation().getReturnDate(),
//                                                          request.getFlightReservation().getOrigin(),
//                                                          request.getFlightReservation().getDestination()));
        Reservation reservation = mapper.map(request, Reservation.class);
        Flight flight = flightRepository.findById(request.getFlightReservation().getFlightNumber()).get();
        reservation.setFlight(flight);
        reservation.setDestination(request.getFlightReservation().getDestination());
        reservationsRepository.save(reservation);
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
    throws ParseException, InvalidOriginException, InvalidDestinationException, InvalidDateRangeException {
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

    /*
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

     */

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

    public void checkDatesAndPlaces(FlightAvailableRequestDTO request)
    throws InvalidDateRangeException, ParseException, InvalidDestinationException, InvalidOriginException {
        // date validation
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateFrom());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateTo());
        if (dateF.after(dateT))
            throw new InvalidDateRangeException();
        // origin and destination validation
        boolean existOrigin = false;
        boolean existDestination = false;
//        for (Flight flight : flightRepository.getFlights()) {
//            if (flight.getOrigin().toUpperCase(Locale.ROOT).equals(request.getOrigin().toUpperCase(Locale.ROOT)))
//                existOrigin = true;
//            if (flight.getDestination().toUpperCase(Locale.ROOT).equals(request.getDestination().toUpperCase(Locale.ROOT)))
//                existDestination = true;
//            if (existOrigin && existDestination)
//                break;
//        }
        if (!existOrigin)
            throw new InvalidOriginException();
        if (!existDestination)
            throw new InvalidDestinationException();
    }
}
