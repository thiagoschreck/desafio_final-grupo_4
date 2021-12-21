package sabre.desafio2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sabre.desafio2.models.DTOs.StatusDTO;
import sabre.desafio2.models.entities.Flight;

import java.util.Date;
import java.util.List;

@Repository
public interface IFlightRepository  extends JpaRepository<Flight,String> {
}