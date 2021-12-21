package sabre.desafio2.models.DTOs;
//O0
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
public class FlightDTO {
    private String flightNumber;
    private String name;
    private String origin;
    private String destination;
    @Pattern( regexp = "^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\\d{4}$", message = "Formato de fecha debe ser dd/mm/aaaa\n")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    private Date goingDate;
    @Pattern( regexp = "^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\\d{4}$", message = "Formato de fecha debe ser dd/mm/aaaa\n")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    private Date returnDate;
    private String seatType;
    private double flightPrice;
}
