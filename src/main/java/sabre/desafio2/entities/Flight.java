package sabre.desafio2.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private String flightNumber;
    private String name;
    private String origin;
    private String destination;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    private Date goingDate;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    private Date returnDate;
    private String seatType;
    private double flightPrice;
}
