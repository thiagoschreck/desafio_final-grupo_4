package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightBookingRequestDTO {
    private String userName;
    private FlightReservationDTO flightReservation;
}
