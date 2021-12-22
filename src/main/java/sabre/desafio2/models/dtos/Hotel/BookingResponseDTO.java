package sabre.desafio2.models.dtos.Hotel;

import bootcamp.AgenciaTurismo.dtos.Shared.StatusDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class BookingResponseDTO {
    @NotEmpty(message = "The username has to be specified")
    @Email(message = "The username has to be a valid email address")
    private String userName;
    @NotNull(message = "The price amount has to be specified")
    @Positive(message = "The price amount has to be positive")
    private double amount;
    @PositiveOrZero(message = "The interest rate can not be negative")
    private double interest;
    @Positive(message = "The total price amount has to be positive")
    private double total;
    @Valid
    @NotNull(message = "The booking information has to be specified")
    private BookingInfoDTO booking;
    @NotNull(message = "The status code has to be specified")
    private StatusDTO statusCode;
}
