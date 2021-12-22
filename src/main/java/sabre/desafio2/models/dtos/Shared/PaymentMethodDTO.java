package sabre.desafio2.models.dtos.Shared;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PaymentMethodDTO {
    @NotEmpty(message = "The type of payment must be specified")
    private String type;
    @NotEmpty(message = "The payment number must be specified")
    private String number;
    @NotNull(message = "The dues amount must be specified")
    @Min(value = 1, message = "The dues amount can not be negative")
    private int dues;
}
