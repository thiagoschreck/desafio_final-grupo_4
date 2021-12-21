package sabre.desafio2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sabre.desafio2.models.entities.FlightReservation;
import sabre.desafio2.models.entities.Reservation;

@Repository
public interface IReservationsRepository extends JpaRepository<Reservation,Long> {
    /*
    @Query("SELECT reservation.reservation_id FROM FlightReservation reservation WHERE reservation.reservation_id = :id")
    Reservation findReservationById(@Param("id") String id);

    @Query("UPDATE reservation SET reservation_id = :newRes.reservationId, going_date = :newRes.goingDate,return_date = :newRes.returnDate,origin = :newRes.origin,destination = :newRes.destination,seats = :newRes.seats , seat_type = :newRes.seatType,payment_method = :newRes.PaymentMethod WHERE [reservation_id] = :newRes.reservationId")
    Reservation updateReservation(@Param("newRes") Reservation newRes);
     */
}
