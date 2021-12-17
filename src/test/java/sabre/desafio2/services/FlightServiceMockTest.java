package sabre.desafio2.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sabre.desafio2.exceptions.NoFlightsException;
import sabre.desafio2.repositories.FlightRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceMockTest {
    @Mock
    FlightRepository flightRepository;
    @InjectMocks
    FlightService flightService;

    @Test
    void getFlightsEmptyList() {
        // arrange
        when(flightRepository.getFlights()).thenReturn(new ArrayList<>());
        // act
        NoFlightsException exception = assertThrows(NoFlightsException.class, () -> flightService.getFlights());
        // assert
        assertEquals("No hay vuelos registrados", exception.ERROR);
    }
}
