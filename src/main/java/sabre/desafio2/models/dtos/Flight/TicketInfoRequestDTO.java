package sabre.desafio2.models.dtos.Flight;

import lombok.Data;
import sabre.desafio2.models.dtos.Shared.PaymentMethodDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class TicketInfoRequestDTO extends TicketInfoDTO {
    @Valid
    @NotNull(message = "The payment method has to be specified")
    private PaymentMethodDTO paymentMethod;
}
