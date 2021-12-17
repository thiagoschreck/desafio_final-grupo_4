package sabre.desafio2.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelBookingErrorDTO {
    private HotelBookingRequestDTO booking;
    private StatusDTO status;
}
