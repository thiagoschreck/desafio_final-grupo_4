package sabre.desafio2.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sabre.desafio2.DTOs.*;
import sabre.desafio2.entities.Hotel;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.repositories.HotelRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelServiceTest {
    HotelService hotelService;
    HotelRepository hotelRepository;

    @BeforeEach
    private void start() {
        hotelService = new HotelService();
        hotelRepository = new HotelRepository();
    }

    private Hotel createHotel() throws ParseException {
        Hotel hotel = new Hotel();
        hotel.setHotelCode("RPH-12200");
        hotel.setName("Regency Park Hotel");
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse("07/12/2021");
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse("09/12/2021");
        hotel.setAvailableFrom(dateF);
        hotel.setAvailableTo(dateT);
        hotel.setPlace("Montevideo");
        hotel.setRoomType("Single");
        hotel.setPriceByNight(345);
        return hotel;
    }

    private HotelBookingRequestDTO createHotelBooking() {
        // create people list and payment method
        List<PeopleDTO> peopleList = new ArrayList<>();
        peopleList.add(new PeopleDTO("12345678", "Maximiliano", "Pan", "04/03/2000", "maxi@mail.com"));
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("Debit", "1234-1234-1234-1234", 1);
        // set booking attributes
        HotelBookingDTO booking = new HotelBookingDTO();
        booking.setDateFrom("18/03/2022");
        booking.setDateTo("21/03/2022");
        booking.setDestination("Buenos Aires");
        booking.setHotelCode("BH-0002");
        booking.setPeopleAmount(1);
        booking.setRoomType("Single");
        booking.setPeople(peopleList);
        booking.setPaymentMethod(paymentMethod);
        // set username and booking
        HotelBookingRequestDTO request = new HotelBookingRequestDTO();
        request.setUserName("Maximiliano");
        request.setBooking(booking);
        return request;
    }

    @Test
    void getHotelsOK() throws NoHotelsException {
        // arrange
        List<Hotel> hotels = hotelRepository.getHotels();
        // act
        HotelResponseListDTO result = hotelService.getHotels();
        // assert
        assertEquals(hotels.size(), result.getHotels().size());
        assertEquals(hotels.get(0).getHotelCode(), result.getHotels().get(0).getHotelCode());
    }

    @Test
    void availableHotelsOK()
    throws DestinationException, DateFromException, ParseException, DateToException, NoHotelsAvailablesException {
        // arrange
        HotelAvailableRequestDTO request = new HotelAvailableRequestDTO("18/03/2022", "20/03/2022", "Buenos Aires");
        // act
        HotelResponseListDTO result = hotelService.availableHotels(request);
        // assert
        assertNotNull(result);
        assertEquals(1, result.getHotels().size());
        assertEquals("BH-0002", result.getHotels().get(0).getHotelCode());
    }

    @Test
    void availableHotelsNoHotels() {
        // arrange
        HotelAvailableRequestDTO request = new HotelAvailableRequestDTO("04/03/2021", "04/03/2023", "Buenos Aires");
        // act
        NoHotelsAvailablesException exception = assertThrows(NoHotelsAvailablesException.class, () -> hotelService.availableHotels(request));
        // assert
        assertEquals("No hay hoteles disponibles con los filtros ingresados", exception.ERROR);
    }

    @Test
    void availableHotelsInvalidDestination() {
        // arrange
        HotelAvailableRequestDTO request = new HotelAvailableRequestDTO("04/03/2021", "04/03/2023", "Montevideo");
        // act
        DestinationException exception = assertThrows(DestinationException.class, () -> hotelService.availableHotels(request));
        // assert
        assertEquals("El destino elegido no existe", exception.getERROR());
    }

    @Test
    void availableHotelsEmptyDestination() {
        // arrange
        HotelAvailableRequestDTO request = new HotelAvailableRequestDTO("04/03/2021", "04/03/2023", "");
        // act
        DestinationException exception = assertThrows(DestinationException.class, () -> hotelService.availableHotels(request));
        // assert
        assertEquals("El destino elegido no existe", exception.getERROR());
    }

    @Test
    void availableHotelsInvalidDateFormat() {
        // arrange
        HotelAvailableRequestDTO request = new HotelAvailableRequestDTO("04/03/2023", "04/03/2021", "Montevideo");
        // act
        DateFromException exception = assertThrows(DateFromException.class, () -> hotelService.availableHotels(request));
        // assert
        assertEquals("La fecha de ida debe ser menor a la de vuelta", exception.getERROR());
    }

    @Test
    void bookHotelDebitOK()
    throws PeopleRoomException, DestinationException, HotelBookingException, DateFromException, ParseException, DateToException {
        // arrange
        HotelBookingRequestDTO request = createHotelBooking();
        // act
        HotelBookingResponseDTO response = hotelService.bookHotel(request);
        // assert
        assertEquals(200, response.getStatusCode().getCode());
        assertEquals("Transaction completed successfully", response.getStatusCode().getMessage());
        assertEquals(0, response.getInterest());
        assertEquals(21600.0, response.getTotal());
    }

    @Test
    void bookHotelCredit3DuesOK()
    throws PeopleRoomException, DestinationException, HotelBookingException, DateFromException, ParseException, DateToException {
        // arrange
        HotelBookingRequestDTO request = createHotelBooking();
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("Credit", "1234-1234-1234-1234", 3);
        request.getBooking().setPaymentMethod(paymentMethod);
        // act
        HotelBookingResponseDTO response = hotelService.bookHotel(request);
        // assert
        assertEquals(5.0, response.getInterest());
        assertEquals(200, response.getStatusCode().getCode());
        assertEquals("Transaction completed successfully", response.getStatusCode().getMessage());
    }

    @Test
    void bookHotelCredit9DuesOK()
    throws PeopleRoomException, DestinationException, HotelBookingException, DateFromException, ParseException, DateToException {
        // arrange
        HotelBookingRequestDTO request = createHotelBooking();
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO("Credit", "1234-1234-1234-1234", 9);
        request.getBooking().setPaymentMethod(paymentMethod);
        // act
        HotelBookingResponseDTO response = hotelService.bookHotel(request);
        // assert
        assertEquals(10., response.getInterest());
        assertEquals(200, response.getStatusCode().getCode());
        assertEquals("Transaction completed successfully", response.getStatusCode().getMessage());
    }

    @Test
    void bookHotelBadPeopleAmount() {
        // arrange
        HotelBookingRequestDTO request = createHotelBooking();
        request.getBooking().setPeopleAmount(3);
        // act
        PeopleRoomException exception = assertThrows(PeopleRoomException.class, () -> hotelService.bookHotel(request));
        // assert
        assertEquals("El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.", exception.getERROR());
    }

    @Test
    void bookHotelNotAvailableDates() {
        // arrange
        HotelBookingRequestDTO request = createHotelBooking();
        request.getBooking().setDateFrom("07/12/2021");
        request.getBooking().setDateTo("09/12/2021");
        // act
        HotelBookingException exception = assertThrows(HotelBookingException.class, () -> hotelService.bookHotel(request));
        // assert
        assertEquals("Transaction failure, hotel not available for given dates", exception.getMessage());
    }

    @Test
    void hotelToDTO()
    throws ParseException {
        // arrange
        Hotel hotel = createHotel();
        // act
        HotelDTO hotelDTO = hotelService.hotelToDTO(hotel);
        // assert
        assertEquals(hotel.getHotelCode(), hotelDTO.getHotelCode());
        assertEquals(hotel.getName(), hotelDTO.getName());
        assertEquals(hotel.getAvailableFrom(), hotelDTO.getAvailableFrom());
        assertEquals(hotel.getAvailableTo(), hotelDTO.getAvailableTo());
        assertEquals(hotel.getPlace(), hotelDTO.getPlace());
        assertEquals(hotel.getPriceByNight(), hotelDTO.getPriceByNight());
    }

    @Test
    void isHotelAvailableOK()
    throws ParseException {
        // arrange
        Hotel hotel = createHotel();
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse("07/12/2021");
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse("08/12/2021");
        // act
        boolean result = hotelService.isHotelAvailable(hotel, dateF, dateT);
        // assert
        assertTrue(result);
    }

    @Test
    void isHotelAvailableBadDates()
    throws ParseException {
        // arrange
        Hotel hotel = createHotel();
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2021");
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse("07/12/2021");
        // act
        boolean result = hotelService.isHotelAvailable(hotel, dateF, dateT);
        // assert
        assertFalse(result);
    }

    @Test
    void getHotelByCode() {
        // act
        Hotel response = hotelService.getHotelByCode("SH-0001");
        // assert
        assertEquals("SH-0001", response.getHotelCode());
        assertEquals("Sheraton 2", response.getName());
        assertEquals(4150, response.getPriceByNight());
    }

}