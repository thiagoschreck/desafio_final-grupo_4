package sabre.desafio2.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FlightReservation implements Serializable {
    @Id
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Reservation reservation;

    @Column(name = "user_name")
    private String userName;
}
