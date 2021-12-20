package sabre.desafio2.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class HotelBooking implements Serializable {
    @Id
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Booking booking;

    @Column(name = "user_name")
    private String userName;
}
