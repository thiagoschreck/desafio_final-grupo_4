package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightBookingResponseDTO {
    private String userName;
    private double amount;
    private double interest;
    private double total;
    private FlightBookingInternalResponseDTO flightReservation;
    private StatusDTO statusCode;
}
