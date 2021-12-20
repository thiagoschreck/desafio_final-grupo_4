package sabre.desafio2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class PaymentMethod {
    @Id
    @Column(name = "payment_method_id")
    private long paymentMethodId;
    private String type;
    private String number;
    private int dues;

    @OneToMany(mappedBy = "paymentMethod")
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "paymentMethod")
    private Set<Reservation> reservations;
}
