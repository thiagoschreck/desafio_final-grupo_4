package sabre.desafio2.models.dtos.Hotel;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class HotelRequestDTO {
    @NotEmpty(message = "The hotel code has to be specified")
    private String hotelCode;
    @NotEmpty(message = "The hotel name has to be specified")
    private String name;
    @NotEmpty(message = "The hotel place has to be specified")
    private String place;
    @NotEmpty(message = "The hotel room type has to be specified")
    private String roomType;
    @NotNull(message = "The hotel price by night has to be specified")
    private int priceByNight;
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\\d{4}$",
            message = "Date format must be dd/mm/aaaa")
    @NotNull(message = "The date from which the hotel is available has to be specified")
    private String availableFrom;
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\\d{4}$",
            message = "Date format must be dd/mm/aaaa")
    @NotNull(message = "The date to which the hotel is available has to be specified")
    private String availableTo;
    @NotNull(message = "The hotel reservation status has to be specified")
    private boolean reserved;
}
