package sabre.desafio2.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sabre.desafio2.exceptions.NoHotelsException;
import sabre.desafio2.repositories.HotelRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceMockTest {
    @Mock
    HotelRepository hotelRepository;
    @InjectMocks
    HotelService hotelService;

    @Test
    void getHotelsEmptyList() {
        // arrange
        when(hotelRepository.getHotels()).thenReturn(new ArrayList<>());
        // act
        NoHotelsException exception = assertThrows(NoHotelsException.class, () -> hotelService.getHotels());
        // assert
        assertEquals("No hay hoteles registrados", exception.ERROR);
    }
}
