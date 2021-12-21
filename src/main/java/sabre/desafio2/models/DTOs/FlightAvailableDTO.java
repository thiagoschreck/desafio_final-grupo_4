package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class FlightAvailableDTO {
    private Date dateFrom;
    private Date dateTo;
    private String origin;
    private String destination;
}
