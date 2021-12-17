package sabre.desafio2.services;

import sabre.desafio2.DTOs.*;
import sabre.desafio2.entities.Flight;
import sabre.desafio2.exceptions.*;

import java.text.ParseException;
import java.util.Date;

public interface IFlightService {
    FlightResponseListDTO getFlights() throws NoFlightsException;

    FlightResponseListDTO availableFlights(FlightAvailableRequestDTO request) throws ParseException, DestinationException, DateFromException, OriginException, NoFlightsAvailablesException;

    FlightBookingResponseDTO bookFlight(FlightBookingRequestDTO request) throws FlightBookingException, ParseException, PeopleRoomException, DestinationException, DateFromException, OriginException;

    FlightResponseDTO flightToDTO(Flight flight);

    boolean isFlightAvailable(Flight flight, String origin, String destination, Date dateF, Date dateT);

    Flight getFlightByNumber(String flightNumber);

    void checkDatesAndPlaces(FlightAvailableRequestDTO request) throws ParseException, DateFromException, DestinationException, OriginException;
}
