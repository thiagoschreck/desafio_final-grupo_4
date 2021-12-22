package sabre.desafio2.controllers;

import Util.Util;
import antlr.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FlightControllerTest {

    @Autowired
    MockMvc mockMVc;

    @Test
    void createFlight() throws Exception {
       this.mockMVc.perform(MockMvcRequestBuilders.post("/api/v1/flights/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.jsonPay()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flightNumber").value("EM-200"));

    }

    @Test
    void createReservation() {
    }

    /*@Test
    void updateFlight() throws Exception {
            mockMVc.perform(MockMvcRequestBuilders.put("/api/v1/hotels/edit", "?hotelCode=CH-0002")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Util.jsonPay()))
                    .andDo(print())
                    .andExpect(jsonPath("$.code").value("Hotel modificado correctamente."));
     }*/



    @Test
    void updateReservation() {
    }

    @Test
    void getFlights() {


    }

    @Test
    void getReservations() {
    }

    @Test
    void deleteFlight() {
    }

    @Test
    void deleteReservation() {
    }
}