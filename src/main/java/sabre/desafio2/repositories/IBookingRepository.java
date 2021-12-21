package sabre.desafio2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sabre.desafio2.models.entities.Booking;

@Repository
public interface IBookingRepository extends JpaRepository<Booking,Long> {
}
