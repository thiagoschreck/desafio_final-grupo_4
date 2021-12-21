package sabre.desafio2.models.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Booking {
    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;
    @Column(name = "date_from")
    private Date dateFrom;
    @Column(name = "date_to")
    private Date dateTo;
    private String destination;
    @Column(name = "people_amount")
    private int peopleAmount;
    @Column(name = "room_type")
    private String roomType;

    @OneToOne(mappedBy = "booking")
    private HotelBooking hotelBooking;

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_code")
    private Hotel hotel;

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToMany(mappedBy = "bookings",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Person> people;
}
