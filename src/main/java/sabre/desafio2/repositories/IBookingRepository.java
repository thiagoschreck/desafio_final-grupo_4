package sabre.desafio2.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import sabre.desafio2.entities.Booking;
import sabre.desafio2.entities.FlightReservation;
import sabre.desafio2.entities.HotelBooking;

public interface IBookingRepository extends JpaRepository<HotelBooking,Integer> {

    @Query("SELECT booking.booking_id FROM Booking booking WHERE booking.booking_id = :id ")
    Booking findBookingBy(@Param("id") String id);
    @Query("UPDATE booking SET booking_id = :newHot.bookingId,date_from = :newHot.dateFrom,date_to = :newHot.dateTo,destination = :newHot.destination,people_amount = :newHot.peopleAmount,room_type = :newHot.roomType WHERE [booking_id] = :newHot.bookingId")
    Booking updateBooking(@Param("id") Booking newHot);
}
