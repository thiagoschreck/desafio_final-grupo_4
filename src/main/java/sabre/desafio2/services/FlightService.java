package sabre.desafio2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sabre.desafio2.DTOs.FlightAvailableRequestDTO;
import sabre.desafio2.DTOs.FlightBookingRequestDTO;
import sabre.desafio2.DTOs.FlightDTO;
import sabre.desafio2.DTOs.StatusDTO;
import sabre.desafio2.entities.Flight;
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

    public StatusDTO createFlight(FlightDTO flight){
        Flight newFlight = dtoToFlight(flight);
        // todo - add flight to db
        return new StatusDTO("Vuelo dado de alta correctamente");
    }

    public StatusDTO createReservation(FlightBookingRequestDTO request)
    throws FlightBookingException, ParseException, DestinationException, DateFromException, OriginException {
        checkDatesAndPlaces(new FlightAvailableRequestDTO(request.getFlightReservation().getDateFrom(),
                                                          request.getFlightReservation().getDateTo(),
                                                          request.getFlightReservation().getOrigin(),
                                                          request.getFlightReservation().getDestination()));
        String origin = request.getFlightReservation().getOrigin();
        String destination = request.getFlightReservation().getDestination();
        String flightNumber = request.getFlightReservation().getFlightNumber();
        Flight flight = getFlightByNumber(flightNumber);
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(request.getFlightReservation().getDateFrom());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(request.getFlightReservation().getDateTo());
        if (!isFlightAvailable(flight, origin, destination, dateF, dateT))
            throw new FlightBookingException(request, "Transaction failure, no flights found");
        // todo - add reservation to db
        return new StatusDTO("Reserva de vuelo dada de alta correctamente");
    }

    // MODIFICACIONES


    // CONSULTAS

    /**
     * gets a list of all registered flights.
     *
     * @return a list of all registered flights.
     */
    public List<FlightDTO> getFlights() throws NoFlightsException {
        List<FlightDTO> listDTO = new ArrayList<>();
        for (Flight flight : flightRepository.getFlights()) {
            listDTO.add(flightToDTO(flight));
        }
        if (listDTO.isEmpty())
            throw new NoFlightsException();
        return listDTO;
    }

    /**
     * gets a list of all registered flights that meet the filters passed as a parameter.
     *
     * @param request DTO containing the filters to apply (dateFrom, dateTo, origin, destination).
     * @return a DTO containing a list of all registered flights that meet the given filters.
     */
    public List<FlightDTO> availableFlights(FlightAvailableRequestDTO request)
            throws ParseException, DestinationException, DateFromException, OriginException, NoFlightsAvailablesException {
        checkDatesAndPlaces(request);
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateFrom());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateTo());
        List<FlightDTO> listDTO = new ArrayList<>();
        for (Flight flight : flightRepository.getFlights()) {
            if (isFlightAvailable(flight, request.getOrigin(), request.getDestination(), dateF, dateT))
                listDTO.add(flightToDTO(flight));
        }
        if (listDTO.isEmpty())
            throw new NoFlightsAvailablesException();
        return listDTO;
    }

    // BAJAS


    // AUX FUNCTIONS

    /**
     * converts a Flight entity to a DTO.
     *
     * @param flight entity to convert.
     * @return a FlightDTO containing the data of the given Flight entity.
     */
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

    /**
     * check that a specific flight is available.
     *
     * @param flight      flight to check if it is available.
     * @param origin      place where the flight should depart.
     * @param destination place where the flight should arrive.
     * @param dateF       flight departure date.
     * @param dateT       flight arrival date.
     * @return true in case the flight matches all the given parameters (origin, destination, dateFrom, dateTo).
     * otherwise, it returns false.
     */
    // todo - replace for query
    public boolean isFlightAvailable(Flight flight, String origin, String destination, Date dateF, Date dateT) {
        return origin.toUpperCase(Locale.ROOT).equals(flight.getOrigin().toUpperCase(Locale.ROOT)) &&
                destination.toUpperCase(Locale.ROOT).equals(flight.getDestination().toUpperCase(Locale.ROOT)) &&
                dateF.equals(flight.getGoingDate()) &&
                dateT.equals(flight.getReturnDate());
    }

    /**
     * get a Flight instance from a given flight number.
     *
     * @param flightNumber
     * @return If there is an instance with the flight number, it returns the instance.
     * otherwise, it returns null.
     */
    // todo - replace for query
    public Flight getFlightByNumber(String flightNumber) {
        for (Flight flight : flightRepository.getFlights()) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    /**
     * aux method to check input of availableFlights method.
     *
     * @param request DTO to check, contains the filters to apply (dateFrom, dateTo, origin, destination).
     */
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
