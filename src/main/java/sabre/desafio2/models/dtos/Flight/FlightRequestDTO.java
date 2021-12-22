package sabre.desafio2.models.dtos.Flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequestDTO {
    @NotEmpty(message = "The flight number has to be specified")
    private String flightNumber;
    @NotEmpty(message = "The flight name has to be specified")
    private String name;
    @NotEmpty(message = "The flight origin has to be specified")
    private String origin;
    @NotEmpty(message = "The flight destination has to be specified")
    private String destination;
    @NotEmpty(message = "The seat type has to be specified")
    private String seatType;
    @NotNull(message = "The price of the flight has to be specified")
    @Positive(message = "The price of the flight has to be positive")
    private Double flightPrice;
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\\d{4}$",
            message = "Date format must be dd/mm/aaaa")
    @NotNull(message = "The departure date has to be specified")
    private String goingDate;
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\\d{4}$",
            message = "Date format must be dd/mm/aaaa")
    @NotNull(message = "The arrival date has to be specified")
    private String returnDate;
}
