package sabre.desafio2.models.dtos.Hotel;

import lombok.Data;
import sabre.desafio2.models.dtos.Shared.PaymentMethodDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class BookingInfoRequestDTO extends BookingInfoDTO {
    @Valid
    @NotNull(message = "The payment method has to be specified")
    private PaymentMethodDTO paymentMethod;
}
