package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelBookingResponseDTO {
    private String userName;
    private double amount;
    private double interest;
    private double total;
    private HotelBookingInternalResponseDTO booking;
    private StatusDTO statusCode;
}
