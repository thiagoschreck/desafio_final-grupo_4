package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelBookingRequestDTO {
    private String userName;
    private HotelBookingDTO booking;
}
