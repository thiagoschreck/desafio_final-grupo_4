package sabre.desafio2.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Package<P> {
    @Id
    @Column(name = "package_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int packageNumber;

    private String name;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "client_id")
    private int ClientId;

    @ManyToOne
    @JoinColumn(name = "bookings_or_reservations_id")
    private BookingsOrReservations bookingsOrReservations;
}
