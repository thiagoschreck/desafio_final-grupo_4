package sabre.desafio2.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hotel {
    @Id
    @Column(name = "hotel_code")
    private String hotelCode;
    private String name;
    private String place;
    @Column(name = "room_type")
    private String roomType;
    @Column(name = "room_price")
    private int roomPrice;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @Column(name = "disponibility_date_from")
    private Date disponibilityDateFrom;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @Column(name = "disponibility_date_to")
    private Date disponibilityDateTo;
    @Column(name = "is_booked")
    private boolean isBooked;

    @OneToMany(mappedBy = "hotel",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Booking> bookings;
}
