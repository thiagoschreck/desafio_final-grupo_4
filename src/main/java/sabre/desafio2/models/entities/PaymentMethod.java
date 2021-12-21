package sabre.desafio2.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentMethod {
    @Id
    @Column(name = "payment_method_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentMethodId;
    private String type;
    private String number;
    private int dues;

    public PaymentMethod(String type, String number, int dues) {
        this.type = type;
        this.number = number;
        this.dues = dues;
    }

    @OneToMany(mappedBy = "paymentMethod")
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "paymentMethod")
    private Set<Reservation> reservations;


}
