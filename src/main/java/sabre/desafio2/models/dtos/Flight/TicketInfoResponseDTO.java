package sabre.desafio2.models.dtos.Flight;

import bootcamp.AgenciaTurismo.dtos.Shared.PersonDTO;
import lombok.Data;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Data
public class TicketInfoResponseDTO extends TicketInfoDTO {
    public TicketInfoResponseDTO(
            @Valid
                    Date dateFrom,
            @Valid
                    Date dateTo,
            @Valid
                    String origin,
            @Valid
                    String destination,
            @Valid
                    String flightNUmber,
            @Valid
                    int seats,
            @Valid
                    String seatType,
            @Valid
                    List<PersonDTO> people
    ) {
        super(
                dateFrom,
                dateTo,
                origin,
                destination,
                flightNUmber,
                seats,
                seatType,
                people
        );
    }
}
