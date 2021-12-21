package sabre.desafio2.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Reservation {
    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;
    @Column(name = "going_date")
    private Date goingDate;
    @Column(name = "return_date")
    private Date returnDate;
    private String origin;
    private String destination;
    private int seats;
    @Column(name = "seat_type")
    private String seatType;

    @OneToOne(mappedBy = "reservation")
    private FlightReservation flightReservation;

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_number")
    private Flight flight;

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToMany(mappedBy = "reservations",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Person> people;
}
