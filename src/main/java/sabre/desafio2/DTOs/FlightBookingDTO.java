package sabre.desafio2.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightBookingDTO {
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[0-2])[- /.](19|20)\\d\\d$", message = "Formato de fecha debe ser dd/mm/aaaa")
    private String dateFrom;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[0-2])[- /.](19|20)\\d\\d$", message = "Formato de fecha debe ser dd/mm/aaaa")
    private String dateTo;
    private String origin;
    private String destination;
    private String flightNumber;
    @Positive(message = "La cantidad de personas debe ser un valor numérico")
    private int seats;
    private String seatType;
    private List<PeopleDTO> people;
    private PaymentMethodDTO paymentMethod;
}
