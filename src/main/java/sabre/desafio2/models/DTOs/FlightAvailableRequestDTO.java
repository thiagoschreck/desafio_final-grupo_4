package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class FlightAvailableRequestDTO {
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[0-2])[- /.](19|20)\\d\\d$", message = "Formato de fecha debe ser dd/mm/aaaa")
    private String dateFrom;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[0-2])[- /.](19|20)\\d\\d$", message = "Formato de fecha debe ser dd/mm/aaaa")
    private String dateTo;
    private String origin;
    private String destination;
}
