package sabre.desafio2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sabre.desafio2.models.entities.Hotel;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel,Integer> {
}
