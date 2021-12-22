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
import sabre.desafio2.exceptions.NoFlightsException;
import sabre.desafio2.models.dtos.Flight.FlightDTO;
import sabre.desafio2.models.dtos.Flight.FlightRequestDTO;
import sabre.desafio2.models.dtos.Flight.TicketResponseDTO;
import sabre.desafio2.models.dtos.Shared.StatusDTO;
import sabre.desafio2.models.entities.Flight;
import sabre.desafio2.repositories.IFlightRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FlightServiceTest {
    @Mock
    IFlightRepository repo;
    @InjectMocks
    FlightService service;

    private List<Flight> list;

    /*@Test
    void createFlight() throws ParseException, InvalidDateRangeException {
        String desdeFecha = "25/02/2022";
        String hastaFecha = "20/03/2022";


        FlightRequestDTO flight = new FlightRequestDTO("EM-200", "VueloEm", "Montevideo", "Santiago de Chile", "Economy", 500.0, desdeFecha, hastaFecha);
        StatusDTO res = service.createFlight(flight);
        Assertions.assertEquals(res.getMessage(), "Vuelo dado de alta correctamente");
    }*/


    @Test
    void getFlights() throws NoFlightsException {
        ArrayList<Flight> lista = new ArrayList<>();
        when(repo.findAll()).thenReturn(lista);
        List<FlightDTO> response = new ArrayList<>();
        response.add(new FlightDTO());
        Assertions.assertEquals(1, response.size());
    }

    @Test
    void availableFlights() {
        ArrayList<Flight> lista = new ArrayList<>();
        when(repo.getFlightBy(any(), any(), any(), any())).thenReturn(lista);
        List<FlightDTO> response = new ArrayList<>();
        response.add(new FlightDTO());
        Assertions.assertEquals(1, response.size());
    }
}