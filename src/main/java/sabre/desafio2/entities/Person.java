package sabre.desafio2.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
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
}
