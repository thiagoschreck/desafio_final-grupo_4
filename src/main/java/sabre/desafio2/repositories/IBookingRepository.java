package sabre.desafio2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sabre.desafio2.models.entities.Booking;

@Repository
public interface IBookingRepository extends JpaRepository<Booking,Long> {
/*
    @Query("SELECT booking.booking_id FROM Booking booking WHERE booking.booking_id = :id ")
    Booking findBookingBy(@Param("id") String id);

    @Query("UPDATE booking SET booking_id = :newHot.bookingId,date_from = :newHot.dateFrom,date_to = :newHot.dateTo,destination = :newHot.destination,people_amount = :newHot.peopleAmount,room_type = :newHot.roomType WHERE [booking_id] = :newHot.bookingId")
    Booking updateBooking(@Param("id") Booking newHot);
 */
}
