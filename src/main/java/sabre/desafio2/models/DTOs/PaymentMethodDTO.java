package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentMethodDTO {
    private String type;
    private String number;
    private int dues;
}
