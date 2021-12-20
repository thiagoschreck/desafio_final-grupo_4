package sabre.desafio2.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flight {
    @Id
    @Column(name = "flight_number")
    private String flightNumber;
    private String name;
    private String origin;
    private String destination;
    @Column(name = "going_date")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    private Date goingDate;
    @Column(name = "return_date")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    private Date returnDate;
    @Column(name = "seat_type")
    private String seatType;
    @Column(name = "flight_price")
    private double flightPrice;

    @OneToMany(mappedBy = "flight",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Reservation> reservations;
}
