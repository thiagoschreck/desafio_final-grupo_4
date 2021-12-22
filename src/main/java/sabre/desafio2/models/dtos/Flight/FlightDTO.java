package sabre.desafio2.models.dtos.Flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class FlightDTO {
    @NotEmpty(message = "The flight number has to be specified")
    private String flightNumber;
    @NotEmpty(message = "The flight name has to be specified")
    private String name;
    @NotEmpty(message = "The flight origin has to be specified")
    private String origin;
    @NotEmpty(message = "The flight destination has to be specified")
    private String destination;
    @NotEmpty(message = "The seat type has to be specified")
    private String seatType;
    @NotNull(message = "The price of the flight has to be specified")
    @Positive(message = "The price of the flight has to be positive")
    private Double flightPrice;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "The departure date has to be specified")
    private Date goingDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "The arrival date has to be specified")
    private Date returnDate;

    public FlightDTO(FlightRequestDTO request) throws ParseException {
        Date goingDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getGoingDate());
        Date returnDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getReturnDate());
        this.setFlightNumber(request.getFlightNumber());
        this.setName(request.getName());
        this.setOrigin(request.getOrigin());
        this.setDestination(request.getDestination());
        this.setSeatType(request.getSeatType());
        this.setFlightPrice(request.getFlightPrice());
        this.setGoingDate(goingDate);
        this.setReturnDate(returnDate);
    }
}
