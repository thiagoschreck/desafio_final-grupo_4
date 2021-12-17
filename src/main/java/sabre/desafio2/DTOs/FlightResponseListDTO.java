package sabre.desafio2.DTOs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlightResponseListDTO {
    List<FlightResponseDTO> flights;

    public FlightResponseListDTO() {
        this.flights = new ArrayList<>();
    }
}
