package sabre.desafio2.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private String dni;
    private String name;
    private String lastname;
    @Column(name = "birth_date")
    private Date birthDate;
    private String mail;

    @ManyToMany
    @JoinTable(
            name = "booking_people",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")
    )
    private Set<Booking> bookings;

    @ManyToMany
    @JoinTable(
            name = "reservation_people",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id")
    )
    private Set<Reservation> reservations;
}
