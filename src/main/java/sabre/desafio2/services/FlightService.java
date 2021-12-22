package sabre.desafio2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sabre.desafio2.exceptions.InvalidDateRangeException;
import sabre.desafio2.exceptions.NoFlightsException;
import sabre.desafio2.exceptions.NoFlightsFoundException;
import sabre.desafio2.models.dtos.Flight.FlightDTO;
import sabre.desafio2.models.dtos.Flight.FlightRequestDTO;
import sabre.desafio2.models.dtos.Shared.StatusDTO;
import sabre.desafio2.models.entities.Flight;
import sabre.desafio2.repositories.IFlightRepository;
import sabre.desafio2.repositories.IReservationsRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    @Autowired
    IFlightRepository flightRepository;
    
    @Autowired
    SharedService sharedService;
    
    public FlightService(IFlightRepository flightRepository, IReservationsRepository reservationsRepository) {
        this.flightRepository = flightRepository;
    }

    public StatusDTO createFlight(FlightRequestDTO request)
    throws ParseException, InvalidDateRangeException {
        FlightDTO flight = new FlightDTO(request);
        if (flight.getGoingDate().after(flight.getReturnDate()))
            throw new InvalidDateRangeException();
        flightRepository.save(sharedService.mapper.map(flight, Flight.class));
        return new StatusDTO("Vuelo dado de alta correctamente");
    }

    public StatusDTO updateFlight(String flightNumber, FlightRequestDTO request)
    throws Exception, InvalidDateRangeException {
        Flight currentFlight = flightRepository.findById(flightNumber).get();
        if (currentFlight == null)
            throw new NoFlightsException();
        FlightDTO newFlight = new FlightDTO(request);
        if (newFlight.getGoingDate().after(newFlight.getReturnDate()))
            throw new InvalidDateRangeException();
        flightRepository.save(sharedService.mapper.map(newFlight, Flight.class));
        return new StatusDTO("Vuelo modificado correctamente");
    }

    public StatusDTO deleteFlight(String flightNumber) throws NoFlightsException {
        if (!flightRepository.existsById(flightNumber))
            throw new NoFlightsException();
        flightRepository.deleteById(flightNumber);
        return new StatusDTO("Vuelo dado de baja correctamente");
    }

    public List<FlightDTO> getFlights() throws NoFlightsException {
        List<Flight> flightList = flightRepository.findAll();
        if (flightList.isEmpty())
            throw new NoFlightsException();
        return flightList.stream().map(flightDTO -> sharedService.mapper.map(flightDTO, FlightDTO.class)).collect(Collectors.toList());
    }

    public List<FlightDTO> availableFlights(String dateFrom,
                                            String dateTo,
                                            String origin,
                                            String destination)
    throws NoFlightsException, ParseException, InvalidDateRangeException, NoFlightsFoundException {
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(dateFrom);
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(dateTo);
        if (dateF.after(dateT))
            throw new InvalidDateRangeException();
        List<FlightDTO> flights = new ArrayList<>();
        for (FlightDTO flight: getFlights())
            if (flight.getGoingDate().compareTo(dateF) == 0 ||
                flight.getReturnDate().compareTo(dateT) == 0 ||
                flight.getOrigin().equalsIgnoreCase(origin) ||
                flight.getDestination().equalsIgnoreCase(destination))
                flights.add(flight);
        if (flights.isEmpty())
            throw new NoFlightsFoundException();
        return flights;
    }
}
