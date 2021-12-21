package sabre.desafio2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sabre.desafio2.models.entities.FlightReservation;
import sabre.desafio2.models.entities.Reservation;

@Repository
public interface IReservationsRepository extends JpaRepository<Reservation,Long> {
}
