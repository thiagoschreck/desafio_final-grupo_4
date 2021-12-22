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
    IReservationsRepository reservationsRepository;
    @Autowired
    SharedService sharedService;

    public FlightService(IFlightRepository flightRepository,
                         IReservationsRepository reservationsRepository,
                         SharedService sharedService) {
        this.flightRepository = flightRepository;
        this.reservationsRepository = reservationsRepository;
        this.sharedService = sharedService;
    }

    public StatusDTO createFlight(FlightRequestDTO request)
            throws ParseException, InvalidDateRangeException {
        FlightDTO flight = new FlightDTO(request);
        if (flight.getGoingDate().after(flight.getReturnDate()))
            throw new InvalidDateRangeException();
        flightRepository.save(sharedService.mapper.map(flight, Flight.class));
        return new StatusDTO("Flight reserved successfully");
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
        return new StatusDTO("Flight modified successfully");
    }

    public StatusDTO deleteFlight(String flightNumber) throws NoFlightsException {
        if (!flightRepository.existsById(flightNumber))
            throw new NoFlightsException();
        flightRepository.deleteById(flightNumber);
        return new StatusDTO("Flight removed successfully");
    }

    public List<FlightDTO> getFlights() throws NoFlightsException {
        List<Flight> flightList = flightRepository.findAll();
        if (flightList.isEmpty())
            throw new NoFlightsException();
        return flightList.stream().map(flight -> sharedService.mapper.map(flight, FlightDTO.class)).collect(Collectors.toList());
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
        for (FlightDTO flight : getFlights())
            if (flight.getGoingDate().compareTo(dateF) == 0 ||
                    flight.getReturnDate().compareTo(dateT) == 0 ||
                    flight.getOrigin().equalsIgnoreCase(origin) ||
                    flight.getDestination().equalsIgnoreCase(destination))
                flights.add(flight);
        if (flights.isEmpty())
            throw new NoFlightsFoundException();
        return flights;
    }

    /*
    public StatusDTO createReservation(FlightBookingRequestDTO request)
    throws ParseException, InvalidOriginException, InvalidDestinationException, InvalidDateRangeException {
        checkDatesAndPlaces(new FlightAvailableRequestDTO(request.getFlightReservation().getGoingDate(),
                                                          request.getFlightReservation().getReturnDate(),
                                                          request.getFlightReservation().getOrigin(),
                                                          request.getFlightReservation().getDestination()));
        Reservation reservation = mapper.map(request, Reservation.class);
        Flight flight = flightRepository.findById(request.getFlightReservation().getFlightNumber()).get();
        reservation.setFlight(flight);
        reservation.setDestination(request.getFlightReservation().getDestination());
        reservationsRepository.save(reservation);
        return new StatusDTO("Reserva de vuelo dada de alta correctamente");
    }

    public StatusDTO updateReservation(String id, FlightBookingRequestDTO request) throws Exception {
        // todo - invalid id exception
        // throw new Exception();
        // FlightReservation reservation = dtoToFlightReservation(request);
        // todo - update reservation to db
        return new StatusDTO("Reserva de vuelo modificada correctamente");
    }

    public List<FlightReservationDTO> getReservations() {
        // todo - get list of Reservations
        // todo - filter
        List<FlightReservationDTO> reservations = new ArrayList<>();
        // todo - convert reservations into DTOs
        return reservations;
    }

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
        for (Flight flight : flightRepository.getFlights()) {
            if (flight.getOrigin().toUpperCase(Locale.ROOT).equals(request.getOrigin().toUpperCase(Locale.ROOT)))
                existOrigin = true;
            if (flight.getDestination().toUpperCase(Locale.ROOT).equals(request.getDestination().toUpperCase(Locale.ROOT)))
                existDestination = true;
            if (existOrigin && existDestination)
                break;
        }
        if (!existOrigin)
            throw new InvalidOriginException();
        if (!existDestination)
            throw new InvalidDestinationException();
    }
    */
}
