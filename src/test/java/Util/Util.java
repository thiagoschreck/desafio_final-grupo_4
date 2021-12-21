package Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import sabre.desafio2.models.DTOs.FlightDTO;
import sabre.desafio2.models.DTOs.HotelDTO;

import java.util.Date;

public class Util {

    public static String jsonPay() throws JsonProcessingException {
        Date desde = new Date(2022,02,26);
        Date hasta = new Date(2022,03,20);
        FlightDTO flight = new FlightDTO("EM-200",
                "VueloEm",
                "Montevideo",
                "Santiago de Chile",
                desde,
                hasta,
                "Economy",
                500.0);
        ObjectWriter writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE,false).writer();
        return writer.writeValueAsString(flight);
    }
}
