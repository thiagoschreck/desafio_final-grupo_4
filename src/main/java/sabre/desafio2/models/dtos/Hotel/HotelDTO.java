package sabre.desafio2.models.dtos.Hotel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
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
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @NotNull(message = "The date from which the hotel is available has to be specified")
    private Date availableFrom;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @NotNull(message = "The date to which the hotel is available has to be specified")
    private Date availableTo;
    @NotNull(message = "The hotel reservation status has to be specified")
    private boolean reserved;

    public HotelDTO(HotelRequestDTO request) throws ParseException {
        hotelCode = request.getHotelCode();
        name = request.getName();
        place = request.getPlace();
        roomType = request.getRoomType();
        priceByNight = request.getPriceByNight();
        availableFrom = new SimpleDateFormat("dd/MM/yyyy").parse(request.getAvailableFrom());
        availableTo = new SimpleDateFormat("dd/MM/yyyy").parse(request.getAvailableTo());
        reserved = request.isReserved();
    }
}
