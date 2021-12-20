package sabre.desafio2.services;

import sabre.desafio2.DTOs.*;
import sabre.desafio2.entities.Flight;
import sabre.desafio2.exceptions.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IFlightService {
    List<FlightDTO> getFlights() throws NoFlightsException;

    List<FlightDTO> availableFlights(FlightAvailableRequestDTO request) throws ParseException, DestinationException, DateFromException, OriginException, NoFlightsAvailablesException;

    StatusDTO bookFlight(FlightBookingRequestDTO request) throws FlightBookingException, ParseException, PeopleRoomException, DestinationException, DateFromException, OriginException;

    FlightDTO flightToDTO(Flight flight);

    boolean isFlightAvailable(Flight flight, String origin, String destination, Date dateF, Date dateT);

    Flight getFlightByNumber(String flightNumber);

    void checkDatesAndPlaces(FlightAvailableRequestDTO request) throws ParseException, DateFromException, DestinationException, OriginException;
}
