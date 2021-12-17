package sabre.desafio2.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sabre.desafio2.DTOs.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FlightControllerTest {

    @Autowired
    MockMvc mockMvc;

    private FlightBookingRequestDTO createFlightBooking() {
        FlightBookingDTO bookingDTO = new FlightBookingDTO();
        bookingDTO.setFlightNumber("BATU-5536");
        bookingDTO.setOrigin("Buenos Aires");
        bookingDTO.setDestination("Tucumán");
        bookingDTO.setDateFrom("10/02/2022");
        bookingDTO.setDateTo("17/02/2022");
        bookingDTO.setSeats(1);
        bookingDTO.setSeatType("Economy");
        ArrayList<PeopleDTO> peopleList = new ArrayList<>();
        peopleList.add(new PeopleDTO("12345678", "Pepito", "Gomez", "10/11/1982", "pepito@mail.com"));
        bookingDTO.setPeople(peopleList);
        PaymentMethodDTO payment = new PaymentMethodDTO("Debit", "1234-5678-9101-1121", 3);
        bookingDTO.setPaymentMethod(payment);
        FlightBookingRequestDTO flightBooking = new FlightBookingRequestDTO();
        flightBooking.setUserName("maxipan@mail.com");
        flightBooking.setFlightReservation(bookingDTO);
        return flightBooking;
    }

    @Test
    void getFlights_whenNoParams_expectAllFlightsList() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/flights", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flights[0].flightNumber").value("BAPI-1235"));
    }

    @Test
    void getFlights_whenRequiredParams_expectAvailableFlightsList() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/flights", "")
                        .param("dateFrom","15/02/2022")
                        .param("dateTo","28/02/2022")
                        .param("origin","Bogotá")
                        .param("destination","Buenos Aires"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flights[0].flightNumber").value("BOBA-6567"));
    }

    @Test
    void bookFlight_whenRequiredParams_expectedStatus200() throws Exception {
        FlightBookingRequestDTO bookingRQ = createFlightBooking();
        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();
        String jsonRQ = writer.writeValueAsString(bookingRQ);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/flight-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRQ))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName")
                        .value("maxipan@mail.com"));
    }

    @Test
    void bookFlight_whenNotAvailableFlightInGivenDate_expectedFlightBookingException() throws Exception {
        FlightBookingRequestDTO bookingRQ = createFlightBooking();
        bookingRQ.getFlightReservation().setDateFrom("10/01/2022");
        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();
        String jsonRQ = writer.writeValueAsString(bookingRQ);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/flight-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRQ))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.message")
                        .value("Transaction failure, no flights found"));
    }
}