package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sabre.desafio2.models.entities.PaymentMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightBookingDTO {
    private String userName;
    private FlightReservationDTO flightReservation;
    private PaymentMethodDTO paymentMethod;
}
