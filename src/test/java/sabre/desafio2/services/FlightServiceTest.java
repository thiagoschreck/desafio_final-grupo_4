package sabre.desafio2.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sabre.desafio2.DTOs.*;
import sabre.desafio2.entities.Flight;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.repositories.FlightRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceTest {
    FlightService flightService;
    FlightRepository flightRepository;

    @BeforeEach
    private void start() {
        flightService = new FlightService();
        flightRepository = new FlightRepository();
    }

    /**
     * aux method that creates a valid Flight.
     * used for testing purposes only.
     * @return a Flight instance with all attributes set.
     */
    private Flight createFlight() throws ParseException {
        Flight flight = new Flight();
        flight.setFlightNumber("BOBA-6567");
        flight.setOrigin("Bogotá");
        flight.setDestination("Buenos Aires");
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse("01/03/2022");
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse("14/03/2022");
        flight.setDateFrom(dateF);
        flight.setDateTo(dateT);
        flight.setSeatType("Economy");
        flight.setPricePerPerson(39860);
        return flight;
    }

    /**
     * aux method that creates a valid FlightBookingRequestDTO.
     * used for testing purposes only.
     * @return a FlightBookingRequestDTO instance with all attributes set.
     */
    private FlightBookingRequestDTO createFlightBooking() {
        // create people list and payment method
        List<PeopleDTO> peopleList = new ArrayList<>();
        peopleList.add(new PeopleDTO("12345678", "Maximiliano", "Pan", "04/03/2000", "maxi@mail.com"));
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("Debit", "1234-1234-1234-1234", 1);
        // set booking attributes
        FlightBookingDTO booking = new FlightBookingDTO();
        booking.setDateFrom("23/01/2022");
        booking.setDateTo("31/1/2022");
        booking.setOrigin("Cartagena");
        booking.setDestination("Medellín");
        booking.setFlightNumber("CAME-0321");
        booking.setSeats(1);
        booking.setSeatType("Economy");
        booking.setPeople(peopleList);
        booking.setPaymentMethod(paymentMethod);
        // set username and booking
        FlightBookingRequestDTO request = new FlightBookingRequestDTO();
        request.setUserName("Maximiliano");
        request.setFlightReservation(booking);
        return request;
    }

    @Test
    void getFlightsOK() throws NoFlightsException {
        // arrange
        List<Flight> flights = flightRepository.getFlights();
        // act
        FlightResponseListDTO result = flightService.getFlights();
        // assert
        assertEquals(flights.size(), result.getFlights().size());
        assertEquals(flights.get(0).getFlightNumber(), result.getFlights().get(0).getFlightNumber());
    }

    @Test
    void availableFlights()
    throws DestinationException, DateFromException, OriginException, ParseException, NoFlightsAvailablesException {
        // arrange
        FlightAvailableRequestDTO request = new FlightAvailableRequestDTO("10/02/2022", "24/02/2022", "Bogotá", "Medellín");
        // act
        FlightResponseListDTO result = flightService.availableFlights(request);
        // assert
        assertNotNull(result);
        assertEquals(1, result.getFlights().size());
        assertEquals("BOME-4442", result.getFlights().get(0).getFlightNumber());
    }

    @Test
    void availableFlightsNoFlights() {
        // arrange
        FlightAvailableRequestDTO request = new FlightAvailableRequestDTO("09/02/2022", "24/02/2022", "Bogotá", "Medellín");
        // act
        NoFlightsAvailablesException exception = assertThrows(NoFlightsAvailablesException.class, () -> flightService.availableFlights(request));
        // assert
        assertEquals("No hay hoteles disponibles con los filtros ingresados", exception.ERROR);
    }

    @Test
    void availableFlightsInvalidOrigin() {
        // arrange
        FlightAvailableRequestDTO request = new FlightAvailableRequestDTO("09/02/2022", "24/02/2022", "Montevideo", "Medellín");
        // act
        OriginException exception = assertThrows(OriginException.class, () -> flightService.availableFlights(request));
        // assert
        assertEquals("El origen elegido no existe", exception.getERROR());
    }

    @Test
    void availableFlightsInvalidDestination() {
        // arrange
        FlightAvailableRequestDTO request = new FlightAvailableRequestDTO("09/02/2022", "24/02/2022", "Bogotá", "Montevideo");
        // act
        DestinationException exception = assertThrows(DestinationException.class, () -> flightService.availableFlights(request));
        // assert
        assertEquals("El destino elegido no existe", exception.getERROR());
    }

    @Test
    void availableFlightsEmptyOrigin() {
        // arrange
        FlightAvailableRequestDTO request = new FlightAvailableRequestDTO("09/02/2022", "24/02/2022", "", "Medellín");
        // act
        OriginException exception = assertThrows(OriginException.class, () -> flightService.availableFlights(request));
        // assert
        assertEquals("El origen elegido no existe", exception.getERROR());
    }

    @Test
    void availableHotelsEmptyDestination() {
        // arrange
        FlightAvailableRequestDTO request = new FlightAvailableRequestDTO("10/02/2022", "24/02/2022", "Bogotá", "");
        // act
        DestinationException exception = assertThrows(DestinationException.class, () -> flightService.availableFlights(request));
        // assert
        assertEquals("El destino elegido no existe", exception.getERROR());
    }

    @Test
    void availableFlightInvalidDateFormat() {
        // arrange
        FlightAvailableRequestDTO request = new FlightAvailableRequestDTO("25/02/2022", "24/02/2022", "Bogotá", "Medellín");
        // act
        DateFromException exception = assertThrows(DateFromException.class, () -> flightService.availableFlights(request));
        // assert
        assertEquals("La fecha de ida debe ser menor a la de vuelta", exception.getERROR());
    }

    @Test
    void bookFlightDebitOK()
    throws DestinationException, DateFromException, ParseException, OriginException, FlightBookingException {
        // arrange
        FlightBookingRequestDTO request = createFlightBooking();
        // act
        FlightBookingResponseDTO response = flightService.bookFlight(request);
        // assert
        assertEquals(200, response.getStatusCode().getCode());
        assertEquals("Transaction completed successfully", response.getStatusCode().getMessage());
        assertEquals(0, response.getInterest());
        assertEquals(7800.0, response.getTotal());
    }

    @Test
    void bookFlightCredit3DuesOK()
    throws DestinationException, DateFromException, ParseException, OriginException, FlightBookingException {
        // arrange
        FlightBookingRequestDTO request = createFlightBooking();
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("Credit", "1234-1234-1234-1234", 3);
        request.getFlightReservation().setPaymentMethod(paymentMethod);
        // act
        FlightBookingResponseDTO response = flightService.bookFlight(request);
        // assert
        assertEquals(200, response.getStatusCode().getCode());
        assertEquals("Transaction completed successfully", response.getStatusCode().getMessage());
        assertEquals(5.0, response.getInterest());
        assertEquals(8190.0, response.getTotal());
    }

    @Test
    void bookFlightCredit9DuesOK()
    throws DestinationException, DateFromException, ParseException, OriginException, FlightBookingException {
        // arrange
        FlightBookingRequestDTO request = createFlightBooking();
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("Credit", "1234-1234-1234-1234", 9);
        request.getFlightReservation().setPaymentMethod(paymentMethod);
        // act
        FlightBookingResponseDTO response = flightService.bookFlight(request);
        // assert
        assertEquals(200, response.getStatusCode().getCode());
        assertEquals("Transaction completed successfully", response.getStatusCode().getMessage());
        assertEquals(10.0, response.getInterest());
        assertEquals(8580.0, response.getTotal());
    }

    @Test
    void bookFlightNotAvailableDates() {
        // arrange
        FlightBookingRequestDTO request = createFlightBooking();
        request.getFlightReservation().setDateFrom("07/12/2021");
        request.getFlightReservation().setDateTo("09/12/2021");
        // act
        FlightBookingException exception = assertThrows(FlightBookingException.class, () -> flightService.bookFlight(request));
        // assert
        assertEquals("Transaction failure, no flights found", exception.getMessage());
    }

    @Test
    void flightToDTO() throws ParseException {
        // arrange
        Flight flight = createFlight();
        // act
        FlightResponseDTO flightDTO = flightService.flightToDTO(flight);
        // asset
        assertEquals(flight.getFlightNumber(), flightDTO.getFlightNumber());
        assertEquals(flight.getOrigin(), flightDTO.getOrigin());
        assertEquals(flight.getDestination(), flightDTO.getDestination());
        assertEquals(flight.getDateFrom(), flightDTO.getDateFrom());
        assertEquals(flight.getDateTo(), flightDTO.getDateTo());
        assertEquals(flight.getPricePerPerson(), flightDTO.getPricePerPerson());
        assertEquals(flight.getSeatType(), flightDTO.getSeatType());
    }

    @Test
    void isFlightAvailableOK()
    throws ParseException {
        // arrange
        Flight flight = createFlight();
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse("01/03/2022");
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse("14/03/2022");
        // act
        boolean result = flightService.isFlightAvailable(flight, "Bogotá", "Buenos Aires", dateF, dateT);
        // assert
        assertTrue(result);
    }

    @Test
    void isFlightAvailableBadDates()
    throws ParseException {
        // arrange
        Flight flight = createFlight();
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2021");
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse("07/12/2021");
        // act
        boolean result = flightService.isFlightAvailable(flight, "Bogotá", "Buenos Aires", dateF, dateT);
        // assert
        assertFalse(result);
    }

    @Test
    void getFlightByCodeOK() {
        // act
        Flight response = flightService.getFlightByNumber("MEPI-9986");
        // assert
        assertEquals("MEPI-9986", response.getFlightNumber());
        assertEquals("Medellín", response.getOrigin());
        assertEquals("Puerto Iguazú", response.getDestination());
        assertEquals(41640, response.getPricePerPerson());
    }

    @Test
    void getFlightByCodeNoExists() {
        // act
        Flight response = flightService.getFlightByNumber("S101-91");
        // assert
        assertNull(response);
    }
}
