package sabre.desafio2.models.DTOs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HotelResponseListDTO {
    List<HotelDTO> hotels;

    public HotelResponseListDTO() {
        this.hotels = new ArrayList<>();
    }
}
