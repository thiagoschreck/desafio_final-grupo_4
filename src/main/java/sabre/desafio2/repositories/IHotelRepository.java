package sabre.desafio2.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sabre.desafio2.DTOs.StatusDTO;
import sabre.desafio2.entities.Booking;
import sabre.desafio2.entities.Hotel;

import java.util.Date;
import java.util.List;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel,Integer> {
    /*
    @Query("INSERT INTO hotel VALUE :newHotel")
    Hotel addHotel(@Param("newHotel") Hotel newHotel);

    @Query("SELECT hotel.hotel_code FROM Hotel hotel WHERE hotel.hotel_code = :code")
    Hotel findHotelByHotelCode(@Param("code")String code);

    @Query("UPDATE hotel SET hotel_code = :newHot.hotelCode, name = :newHot.name,place = :newHot.place,room_type =  :newHot.roomType, room_price = :newHot.roomPrice,disponibility_date_from = :newHot.disponibilityDateFrom,disponibility_date_to = :newHot.disponibilityDateFrom,is_booked = :newHot.isBooked WHERE [hotel_code] = :newHot.hotelCode")
    Hotel updateHotel(@Param("newHot") Hotel newHot);

    @Query("SELECT flight FROM Hotel WHERE hotel.disponibility_date_to = :dateTo AND hotel.disponibility_date_from = :dateFrom AND hotel.place = :place")
    List<Hotel> getHotelBy(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo, @Param("place") String place);

    @Query("DELETE FROM hotel WHERE booking_id = :id")
    StatusDTO deleteHotelByBookingId(@Param("id")String id);

    @Query("DELETE FROM booking WHERE booking_id = :id")
    StatusDTO deleteBookingById(@Param("id") String id);
     */
}
