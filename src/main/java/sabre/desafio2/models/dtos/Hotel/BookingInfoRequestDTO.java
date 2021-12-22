package sabre.desafio2.models.dtos.Hotel;

import bootcamp.AgenciaTurismo.dtos.Shared.PaymentMethodDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BookingInfoRequestDTO extends BookingInfoDTO {
    @Valid
    @NotNull(message = "The payment method has to be specified")
    private PaymentMethodDTO paymentMethod;
}
