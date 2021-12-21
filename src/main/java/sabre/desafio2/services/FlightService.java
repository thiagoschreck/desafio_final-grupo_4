package sabre.desafio2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sabre.desafio2.exceptions.InvalidDateRangeException;
import sabre.desafio2.exceptions.InvalidDestinationException;
import sabre.desafio2.exceptions.InvalidOriginException;
import sabre.desafio2.exceptions.NoFlightsException;
import sabre.desafio2.models.DTOs.FlightAvailableRequestDTO;
import sabre.desafio2.models.DTOs.FlightDTO;
import sabre.desafio2.models.DTOs.StatusDTO;
import sabre.desafio2.models.entities.Flight;
import sabre.desafio2.models.entities.Reservation;
import sabre.desafio2.repositories.IFlightRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FlightService {
    @Autowired
    IFlightRepository flightRepository;

    public FlightService(IFlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    /**
     *
     * @param flightDTO
     * @return
     */
    public StatusDTO createFlight(FlightDTO flightDTO) throws InvalidDateRangeException, ParseException {
        Flight flight = dtoToFlight(flightDTO);
        if (flight.getGoingDate().after(flight.getReturnDate()))
            throw new InvalidDateRangeException();
        flightRepository.save(flight);
        return new StatusDTO("Vuelo dado de alta correctamente");
    }

    /**
     *
     * @param flightNumber
     * @param flightDTO
     * @return
     */
    public StatusDTO updateFlight(String flightNumber, FlightDTO flightDTO) throws Exception {
        Flight flight = flightRepository.findById(flightNumber).get();
        if(flight == null)
            throw new NoFlightsException();
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(flightDTO.getGoingDate());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(flightDTO.getReturnDate());
        Flight flightUpd = new Flight( flightDTO.getFlightNumber(),
                flightDTO.getName(), flightDTO.getOrigin(), flightDTO.getDestination(), dateF,
                dateT, flightDTO.getSeatType(), flightDTO.getFlightPrice(),null);
        flightRepository.save(flightUpd);
        return new StatusDTO("Vuelo modificado correctamente");
    }

    /**
     *
     * @return
     */
    public List<FlightDTO> getFlights() throws NoFlightsException {
        // todo - get list of Flights
        List<FlightDTO> flights = new ArrayList<>();
        // todo - convert flights into DTOs
        return flights;
    }

    /**
     *
     * @param request
     * @return
     */
    public List<FlightDTO> availableFlights(FlightAvailableRequestDTO request)
    throws ParseException, InvalidOriginException, InvalidDestinationException, InvalidDateRangeException {
        // todo - get list of Flights
        // todo - filter
        List<FlightDTO> flights = new ArrayList<>();
        // todo - convert flights into DTOs
        return flights;
    }

    /**
     *
     * @param flightNumber
     * @return
     */
    public StatusDTO deleteFlight(String flightNumber) throws NoFlightsException {
        Flight flight = flightRepository.findById(flightNumber).get();
        if (flight == null) {
            throw new NoFlightsException();
        }
        flightRepository.delete(flight);
        return new StatusDTO("Vuelo dado de baja correctamente");
    }

    // AUX FUNCTIONS

    public Flight dtoToFlight(FlightDTO flight) throws ParseException {
        // todo - reservations ??
        Set<Reservation> reservations = new HashSet<>();
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(flight.getGoingDate());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(flight.getReturnDate());
        return new Flight(flight.getFlightNumber(),
                flight.getName(),
                flight.getOrigin(),
                flight.getDestination(),
                dateF,
                dateT,
                flight.getSeatType(),
                flight.getFlightPrice(),
                reservations);
    }

}
