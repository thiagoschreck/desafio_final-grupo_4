package sabre.desafio2.models.dtos.Flight;

import bootcamp.AgenciaTurismo.dtos.Flight.TicketInfoDTO;
import bootcamp.AgenciaTurismo.dtos.Shared.PaymentMethodDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class TicketInfoRequestDTO extends TicketInfoDTO {
    @Valid
    @NotNull(message = "The payment method has to be specified")
    private PaymentMethodDTO paymentMethod;
}
