package sabre.desafio2.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightReservation implements Serializable {
    @Id
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Reservation reservation;

    @Column(name = "user_name")
    private String userName;
}
