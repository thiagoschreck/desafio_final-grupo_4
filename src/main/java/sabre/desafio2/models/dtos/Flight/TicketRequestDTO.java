package sabre.desafio2.models.dtos.Flight;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TicketRequestDTO {
    @NotEmpty(message = "The username has to be specified")
    @Email(message = "The username has to be a valid email address")
    private String userName;
    @Valid
    @NotNull(message = "The ticket information has to be specified")
    private TicketInfoRequestDTO flightReservation;
}
