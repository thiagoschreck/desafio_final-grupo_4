package sabre.desafio2.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class BookingsOrReservations<T> {
    @Id
    @Column(name = "bookings_or_reservations_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingsOrReservationsId;

    @ManyToOne
    @JoinColumn(name = "book_res_1")
    private T bookRes1;
    @ManyToOne
    @JoinColumn(name = "book_res_2")
    private T bookRes2;

    @OneToMany(mappedBy = "bookingsOrReservations")
    private Set<Package> packages;
}
