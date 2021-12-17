package sabre.desafio2.entities;

import javax.persistence.*;

@Entity
public class HotelBooking {
    @Id
    @Column(name = "user_name")
    private String userName;
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id")
    private Booking booking;
}
