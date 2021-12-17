package sabre.desafio2.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import sabre.desafio2.entities.Hotel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Data
@Repository
public class HotelRepository implements IHotelRepository {
    private List<Hotel> hotels;

    /**
     * creates a list of Hotel instances using the flights.json file data.
     */
    public HotelRepository() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:static/hotels.json");
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Hotel>> typeRef = new TypeReference<>() {
            };
            this.hotels = mapper.readValue(file, typeRef);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * updates the information of a Hotel instance in the hotels list.
     *
     * @param hotel updated Hotel instance.
     */
    public void createReservation(Hotel hotel) {
        for (Hotel h : this.hotels) {
            if (h.getHotelCode().equals(hotel.getHotelCode())) {
                h.setAvailableFrom(hotel.getAvailableFrom());
            }
        }
    }
}
