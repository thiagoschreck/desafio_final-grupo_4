package sabre.desafio2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sabre.desafio2.models.entities.Flight;

@Repository
public interface IFlightRepository  extends JpaRepository<Flight,String> {
    /*
    @Query("INSERT INTO flight VALUE :newFlight")
    Flight addFlight(@Param("newFlight") Flight newFlight);

    @Query("SELECT flight.flight_number from Flight flight WHERE flight.flight_number = :id")
    Flight findFlightByFlightNumber(@Param("id") String id);

    @Query("UPDATE flight SET flight_number = :newFlight.flightNumber, name = :newFlight.name, origin = :newFlight.origin,destinationne = :newFlight.destination,going_date = :newFlight.goingDate,return_date = :newFlight.returnDate, seat_type = :newFlight.seatType, flight_price = :newFlight.priceflight WHERE [flight_number] = :newFlight.flightNumber")
    Flight updateFlight(@Param("newFlight") Flight newFlight);

    @Query("SELECT flight FROM Flight flight WHERE flight.going_date = :dateFrom AND flight.return_date = :dateTo AND flight.origin = :origin AND flight.destination = :destination ")
    List<Flight> getFlightBy(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo, @Param("origin") String origin, @Param("destination") String destination);

    @Query("DELETE FROM flight WHERE flight_number = :id")
    StatusDTO deleteFlightByFlightNumber(@Param("id") String id);

    @Query("DELETE FROM reservation WHERE reservation_id = :id")
    StatusDTO deleteFlightReservationByCode(@Param("id") String id);
     */
}