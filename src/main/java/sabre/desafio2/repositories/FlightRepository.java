package sabre.desafio2.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import sabre.desafio2.entities.Flight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Data
@Repository
public class FlightRepository implements IFlightRepository {
    private List<Flight> flights;

    /**
     * creates a list of Flight instances using the flights.json file data.
     */
    public FlightRepository() {
        File file = null;
        List<Flight> flightsList = null;
        try {
            file = ResourceUtils.getFile("classpath:static/flights.json");
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Flight>> typeRef = new TypeReference<>() {
            };
            flightsList = mapper.readValue(file, typeRef);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.flights = flightsList;
    }
}
