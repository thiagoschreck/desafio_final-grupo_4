package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelBookingInternalResponseDTO {
    private String dateFrom;
    private String dateTo;
    private String destination;
    private String hotelCode;
    private int peopleAmount;
    private String roomType;
    private List<PeopleDTO> people;
}
