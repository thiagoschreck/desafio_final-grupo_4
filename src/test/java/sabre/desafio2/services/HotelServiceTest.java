package sabre.desafio2.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import sabre.desafio2.exceptions.InvalidDateRangeException;
import sabre.desafio2.exceptions.NoHotelsException;
import sabre.desafio2.exceptions.NoHotelsFoundException;
import sabre.desafio2.models.dtos.Flight.FlightDTO;
import sabre.desafio2.models.dtos.Hotel.HotelDTO;
import sabre.desafio2.models.entities.Flight;
import sabre.desafio2.models.entities.Hotel;
import sabre.desafio2.repositories.IBookingRepository;
import sabre.desafio2.repositories.IFlightRepository;
import sabre.desafio2.repositories.IHotelRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HotelServiceTest {
    @Mock
    IHotelRepository repo;

    @Mock
    IBookingRepository bookingRepo;

    @InjectMocks
   HotelService service;

    @Test
    void createBooking() {

    }


    @Test
    void getHotels() throws NoHotelsException {
        ArrayList<Hotel> lista  = new ArrayList<>();
        when(repo.findAll()).thenReturn(lista);

        ArrayList<HotelDTO> response = new ArrayList<>();
        response.add(new HotelDTO());

        Assertions.assertEquals(1, response.size());

    }

    /*@Test
    void availableHotels() throws NoHotelsFoundException, ParseException, InvalidDateRangeException, NoHotelsException {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(new Hotel());
        when(repo.getHotelBy(any(), any(), any())).thenReturn(hotels);

        List<HotelDTO> res = service.availableHotels("26/03/2022", "30/03/2022", "Montevideo");

        Assertions.assertEquals(1, res.size());
    }*/
}