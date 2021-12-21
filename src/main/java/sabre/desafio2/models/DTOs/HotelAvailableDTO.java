package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class HotelAvailableDTO {
    private Date dateFrom;
    private Date dateTo;
    private String destination;
}
