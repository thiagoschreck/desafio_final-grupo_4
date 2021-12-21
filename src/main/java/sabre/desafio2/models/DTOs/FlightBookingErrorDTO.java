package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightBookingErrorDTO {
    private FlightBookingRequestDTO booking;
    private StatusDTO status;
}