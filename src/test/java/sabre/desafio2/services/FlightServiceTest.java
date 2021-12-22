package sabre.desafio2.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import sabre.desafio2.exceptions.InvalidDateRangeException;
import sabre.desafio2.exceptions.InvalidDestinationException;
import sabre.desafio2.exceptions.InvalidOriginException;
import sabre.desafio2.models.DTOs.*;
import sabre.desafio2.models.entities.*;
import sabre.desafio2.repositories.IFlightRepository;

import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {
 @Mock
 IFlightRepository repo;
 @InjectMocks
 FlightService service;

    @Test
    void createFlight() throws InvalidDateRangeException {//Arrange
       Date desde = new Date(2022,02,26);
       Date hasta = new Date(2022,03,20);
       FlightDTO flight = new FlightDTO ("EM-200",
               "VueloEm",
               "Montevideo",
               "Santiago de Chile",
               desde,
               hasta,
               "Economy",
               500.0);
     service.createFlight(flight);

     Mockito.verify(repo, atLeastOnce()).save(any());

    }
   /* @Test
    void createReservation() throws InvalidOriginException, InvalidDestinationException, ParseException, InvalidDateRangeException {
        PaymentMethodDTO pay = new PaymentMethodDTO("CREDIT",
        "1234-1234-1234-1234",
        6);
      ArrayList<PeopleDTO> people = new ArrayList<>();
      people.add(new PeopleDTO("12345678",
               "Pepito",
                "Gomez",
                "10/11/1982",
                "arjonamiguel@gmail.com"));
      people.add(new PeopleDTO("13345678",
                "Fulanito",
                "Gomez",
                "10/11/1983",
                "arjonamiguel2@gmail.com"));
        FlightReservationDTO reservation = new FlightReservationDTO(
                268,
                "2022-02-26",
                "2022-03-20",
                "Montevideo",
                "Santiago de Chile",
                "EM-200",
                2,
                "Economy",people,pay);
        FlightBookingRequestDTO book = new FlightBookingRequestDTO("Pepito Gom",reservation);
        StatusDTO status =service.createReservation(book);
        Assertions.assertEquals("Reserva de vuelo dada de alta correctamente",status);

    }*/

    /*@Test
    void updateFlight() throws Exception, InvalidDateRangeException {
       when(repo.findByFlightNumber(any())).thenReturn(new Flight());

       service.updateFlight("EM-200",any());

       Mockito.verify(repo, atLeastOnce()).save(any());

    }*/

    @Test
    void updateReservation() {
    }

    @Test
    void getFlights() {
    }

    @Test
    void availableFlights() {
    }

    @Test
    void getReservations() {
    }

    @Test
    void deleteFlight() {
    }

    @Test
    void deleteReservation() {
    }

    @Test
    void dtoToFlightReservation() {
    }

    @Test
    void checkDatesAndPlaces() {
    }
}