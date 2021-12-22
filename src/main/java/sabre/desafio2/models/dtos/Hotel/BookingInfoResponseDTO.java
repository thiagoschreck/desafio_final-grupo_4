package sabre.desafio2.models.dtos.Hotel;

import lombok.Data;
import sabre.desafio2.models.dtos.Shared.PersonDTO;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Data
public class BookingInfoResponseDTO extends BookingInfoDTO {
    public BookingInfoResponseDTO(
            @Valid
                    Date dateFrom,
            @Valid
                    Date dateTo,
            @Valid
                    String destination,
            @Valid
                    String hotelCode,
            @Valid
                    int peopleAmount,
            @Valid
                    String roomType,
            @Valid
                    List<PersonDTO> people
    ) {
        super(
                dateFrom,
                dateTo,
                destination,
                hotelCode,
                peopleAmount,
                roomType,
                people
        );
    }
}
