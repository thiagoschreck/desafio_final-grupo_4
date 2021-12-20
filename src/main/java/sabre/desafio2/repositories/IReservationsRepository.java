package sabre.desafio2.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import sabre.desafio2.entities.Flight;
import sabre.desafio2.entities.FlightReservation;
import sabre.desafio2.entities.Reservation;

public interface IReservationsRepository extends JpaRepository<FlightReservation,Integer> {
    /*
    @Query("SELECT reservation.reservation_id FROM FlightReservation reservation WHERE reservation.reservation_id = :id")
    Reservation findReservationById(@Param("id") String id);

    @Query("UPDATE reservation SET reservation_id = :newRes.reservationId, going_date = :newRes.goingDate,return_date = :newRes.returnDate,origin = :newRes.origin,destination = :newRes.destination,seats = :newRes.seats , seat_type = :newRes.seatType,payment_method = :newRes.PaymentMethod WHERE [reservation_id] = :newRes.reservationId")
    Reservation updateReservation(@Param("newRes") Reservation newRes);
     */
}
