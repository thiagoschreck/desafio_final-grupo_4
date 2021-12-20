package sabre.desafio2.services;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sabre.desafio2.DTOs.*;
import sabre.desafio2.entities.Flight;
import sabre.desafio2.entities.Hotel;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.repositories.FlightRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class FlightService implements IFlightService {
    @Autowired
    FlightRepository flightRepository = new FlightRepository();

    /**
     * gets a list of all registered flights.
     *
     * @return a DTO containing a list of all registered flights.
     */
    public FlightResponseListDTO getFlights() throws NoFlightsException {
        FlightResponseListDTO listDTO = new FlightResponseListDTO();
        for (Flight flight : flightRepository.getFlights()) {
            listDTO.getFlights().add(flightToDTO(flight));
        }
        if (listDTO.getFlights().isEmpty())
            throw new NoFlightsException();
        return listDTO;
    }

    /**
     * gets a list of all registered flights that meet the filters passed as a parameter.
     *
     * @param request DTO containing the filters to apply (dateFrom, dateTo, origin, destination).
     * @return a DTO containing a list of all registered flights that meet the given filters.
     */
    public FlightResponseListDTO availableFlights(FlightAvailableRequestDTO request)
    throws ParseException, DestinationException, DateFromException, OriginException, NoFlightsAvailablesException {
        checkDatesAndPlaces(request);
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateFrom());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateTo());
        FlightResponseListDTO listDTO = new FlightResponseListDTO();
        for (Flight flight : flightRepository.getFlights()) {
            if (isFlightAvailable(flight, request.getOrigin(), request.getDestination(), dateF, dateT))
                listDTO.getFlights().add(flightToDTO(flight));
        }
        if (listDTO.getFlights().isEmpty())
            throw new NoFlightsAvailablesException();
        return listDTO;
    }

    /**
     * make a flight reservation with the data passed as a parameter.
     *
     * @param request DTO containing the data to make the reservation.
     * @return a DTO containing the input given for the reservation, the price and the status of the transaction.
     */
    public FlightBookingResponseDTO bookFlight(FlightBookingRequestDTO request)
    throws FlightBookingException, ParseException, DestinationException, DateFromException, OriginException {
        double interest = calculateInterest(request.getFlightReservation().getPaymentMethod());
        checkDatesAndPlaces(new FlightAvailableRequestDTO(request.getFlightReservation().getDateFrom(),
                request.getFlightReservation().getDateTo(),
                request.getFlightReservation().getOrigin(),
                request.getFlightReservation().getDestination()));
        FlightBookingInternalResponseDTO booking = createInternalBooking(request);
        Flight flight = getFlightByNumber(booking.getFlightNumber());
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(booking.getDateFrom());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(booking.getDateTo());
        if (!isFlightAvailable(flight, booking.getOrigin(), booking.getDestination(), dateF, dateT))
            throw new FlightBookingException(request, "Transaction failure, no flights found");
        FlightBookingResponseDTO response = new FlightBookingResponseDTO();
        response.setUserName(request.getUserName());
        response.setAmount(flight.getFlightPrice());
        response.setInterest(interest);
        response.setTotal(response.getAmount() + response.getAmount() * (interest / 100));
        response.setFlightReservation(booking);
        response.setStatusCode(new StatusDTO("Transaction completed successfully"));
        return response;
    }

    /**
     *
     * @param request
     * @return
     */
    private FlightBookingInternalResponseDTO createInternalBooking(FlightBookingRequestDTO request) {
        FlightBookingInternalResponseDTO booking = new FlightBookingInternalResponseDTO();
        booking.setDateFrom(request.getFlightReservation().getDateFrom());
        booking.setDateTo(request.getFlightReservation().getDateTo());
        booking.setOrigin(request.getFlightReservation().getOrigin());
        booking.setDestination(request.getFlightReservation().getDestination());
        booking.setFlightNumber(request.getFlightReservation().getFlightNumber());
        booking.setSeats(request.getFlightReservation().getSeats());
        booking.setSeatType(request.getFlightReservation().getSeatType());
        booking.setPeople(request.getFlightReservation().getPeople());
        return booking;
    }

    /**
     * calculates the interest of a transaction
     *
     * @param payment
     * @return
     */
    private double calculateInterest(PaymentMethodDTO payment) {
        double interest = 0;
        if (payment.getType().equalsIgnoreCase("Credit")) {
            if (payment.getDues() < 4)
                interest = 5;
            else
                interest = 10;
        }
        return interest;
    }

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
    public boolean isFlightAvailable(Flight flight, String origin, String destination, Date dateF, Date dateT) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
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
    public Flight dtoToFlight(FlightDTO flight) {
        return new Flight(flight.getFlightNumber(),
                flight.getName(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getGoingDate(),
                flight.getReturnDate(),
                flight.getSeatType(),
                flight.getFlightPrice());
    }
    public StatusDTO createFlight(FlightDTO flight){
       Flight newFlight = dtoToFlight(flight);
        // todo - add hotel to db
        return new StatusDTO("Vuelo dado de alta/baja/modificado correctamente");
    }
    public StatusDTO modifyFlight(String flightNumber,FlightDTO flight){
        Flight oldFlight = getFlightByNumber(flightNumber);
        if(oldFlight == null){
            createFlight(flight);
        }else {
            if (!(flight.getName().equals(oldFlight.getName()))) {
                oldFlight.setName(flight.getName());
            }
            if (!(flight.getFlightPrice() == oldFlight.getFlightPrice())) {
                oldFlight.setFlightPrice(flight.getFlightPrice());
            }
            if (!(flight.getReturnDate().equals(oldFlight.getReturnDate()))) {
                oldFlight.setReturnDate(flight.getReturnDate());
            }
            if (!(flight.getGoingDate().equals(oldFlight.getGoingDate()))) {
                oldFlight.setGoingDate(flight.getGoingDate());
            }
            if (!(flight.getDestination().equals(oldFlight.getDestination()))) {
                oldFlight.setDestination(flight.getDestination());
            }
            if (!(flight.getOrigin().equals(oldFlight.getOrigin()))) {
                oldFlight.setOrigin(flight.getOrigin());
            }
            if (!(flight.getSeatType().equals(oldFlight.getSeatType()))) {
                oldFlight.setSeatType(flight.getSeatType());
            }
        }
        return new StatusDTO("Vuelo dado de alta/baja/modificado correctamente");
    }
     //deletee
}
