package sabre.desafio2.DTOs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HotelResponseListDTO {
    List<HotelResponseDTO> hotels;

    public HotelResponseListDTO() {
        this.hotels = new ArrayList<>();
    }
}
