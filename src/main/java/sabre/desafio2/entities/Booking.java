package sabre.desafio2.entities;

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


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_code")
    private Hotel hotel;
    @ManyToMany(mappedBy = "bookings", cascade = CascadeType.ALL)
    private Set<Person> people;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_booking_id", referencedColumnName = "hotel_booking_id"))
    private HotelBooking hotelBooking;
}
