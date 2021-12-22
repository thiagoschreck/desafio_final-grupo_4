package sabre.desafio2.models.dtos.Hotel;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BookingRequestDTO {
    @NotEmpty(message = "The username has to be specified")
    @Email(message = "The username has to be a valid email address")
    private String userName;
    @Valid
    @NotNull(message = "The booking information has to be specified")
    private BookingInfoRequestDTO booking;
}
