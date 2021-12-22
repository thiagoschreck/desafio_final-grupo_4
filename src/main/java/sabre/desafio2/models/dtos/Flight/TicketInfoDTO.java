package sabre.desafio2.models.dtos.Flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sabre.desafio2.models.dtos.Shared.PersonDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketInfoDTO {
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @NotNull(message = "The departure date has to be specified")
    private Date dateFrom;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @NotNull(message = "The arrival date has to be specified")
    Date dateTo;
    @NotBlank(message = "The flight origin has to be specified")
    private String origin;
    @NotBlank(message = "The flight destination has to be specified")
    private String destination;
    @NotBlank(message = "The flight number has to be specified")
    private String flightNumber;
    @NotNull(message = "The amount of seats has to be specified")
    @Positive(message = "The amount of seats has to be positive")
    private int seats;
    @NotBlank(message = "The seat type has to be specified")
    private String seatType;
    @NotEmpty(message = "The amount of people can not be less than 1")
    List<PersonDTO> people;
}
