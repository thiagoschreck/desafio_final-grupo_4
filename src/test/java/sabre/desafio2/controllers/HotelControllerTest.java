package sabre.desafio2.controllers;

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
import sabre.desafio2.DTOs.HotelBookingDTO;
import sabre.desafio2.DTOs.HotelBookingRequestDTO;
import sabre.desafio2.DTOs.PaymentMethodDTO;
import sabre.desafio2.DTOs.PeopleDTO;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerTest {

    @Autowired
    MockMvc mockMvc;

    private HotelBookingRequestDTO createHotelBooking() {
        HotelBookingDTO bookingDTO = new HotelBookingDTO();
        bookingDTO.setDateFrom("17/03/2022");
        bookingDTO.setDateTo("20/03/2022");
        bookingDTO.setDestination("Buenos Aires");
        bookingDTO.setHotelCode("BH-0002");
        bookingDTO.setPeopleAmount(2);
        bookingDTO.setRoomType("Doble");
        ArrayList<PeopleDTO> peopleList = new ArrayList<>();
        peopleList.add(new PeopleDTO("12345678", "Pepito", "Gomez", "10/11/1982", "pepito@mail.com"));
        peopleList.add(new PeopleDTO("12345678", "Fulanito", "Gomez", "29/07/1979", "fulanito@dominio.com"));
        bookingDTO.setPeople(peopleList);
        PaymentMethodDTO payment = new PaymentMethodDTO("Debit", "1234-5678-9101-1121", 3);
        bookingDTO.setPaymentMethod(payment);
        HotelBookingRequestDTO hotelBooking = new HotelBookingRequestDTO();
        hotelBooking.setUserName("maxipan@mail.com");
        hotelBooking.setBooking(bookingDTO);
        return hotelBooking;
    }

    @Test
    void getAllHotels() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hotels", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hotels[0].hotelCode").value("CH-0002"));
    }

    @Test
    void getAvailableHotels() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hotels")
                        .param("dateFrom","18/03/2022")
                        .param("dateTo","20/03/2022")
                        .param("destination","Buenos Aires"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hotels[0].hotelCode").value("BH-0002"));
    }

    @Test
    public void bookHotelOK() throws Exception {
        HotelBookingRequestDTO bookingRQ = createHotelBooking();
        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();
        String jsonRQ = writer.writeValueAsString(bookingRQ);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRQ))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName")
                        .value("maxipan@mail.com"));
    }
}