package sabre.desafio2.models.dtos.Hotel;

import bootcamp.AgenciaTurismo.dtos.Shared.PersonDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfoDTO {
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @NotNull(message = "Booking dates has to be specified")
    @FutureOrPresent(message = "The booking dates can not be in the past")
    private Date dateFrom;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @NotNull(message = "Booking dates has to be specified")
    @FutureOrPresent(message = "The booking dates can not be in the past")
    private Date dateTo;
    @NotEmpty(message = "The booking destination has to be specified")
    private String destination;
    @NotEmpty(message = "The code of the booked hotel has to be specified")
    private String hotelCode;
    @NotNull(message = "The amount of people has to be specified")
    @Positive(message = "The amount of people can not be less than 1")
    private int peopleAmount;
    @NotEmpty(message = "The room type has to be specified")
    private String roomType;
    @NotEmpty(message = "The amount of people can not be less than 1")
    private List<PersonDTO> people;
}
