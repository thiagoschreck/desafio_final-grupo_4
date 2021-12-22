package sabre.desafio2.models.dtos.Flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
public class FlightDTO {
    @NotEmpty(message = "The flight number has to be specified")
    private String flightNumber;
    @NotEmpty(message = "The flight origin has to be specified")
    private String origin;
    @NotEmpty(message = "The flight destination has to be specified")
    private String destination;
    @NotNull(message = "The price of the flight has to be specified")
    @Positive(message = "The price of the flight has to be positive")
    private int price;
    @NotEmpty(message = "The seat type has to be specified")
    private String seatType;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\\d{4}$",
            message = "Date format must be dd/mm/aaaa")
    @NotNull(message = "The departure date has to be specified")
    private Date dateFrom;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\\d{4}$",
            message = "Date format must be dd/mm/aaaa")
    @NotNull(message = "The arrival date has to be specified")
    private Date dateTo;
}
