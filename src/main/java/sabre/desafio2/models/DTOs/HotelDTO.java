package sabre.desafio2.models.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class HotelDTO {
    private String hotelCode;
    private String name;
    private String place;
    private String roomType;
    private int roomPrice;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    private Date disponibilityDateFrom;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    private Date disponibilityDateTo;
    private boolean isBooking;
}
