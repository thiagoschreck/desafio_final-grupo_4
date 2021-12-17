package sabre.desafio2.services;

import sabre.desafio2.DTOs.*;
import sabre.desafio2.entities.Hotel;
import sabre.desafio2.exceptions.*;

import java.text.ParseException;
import java.util.Date;

public interface IHotelService {
    HotelResponseListDTO getHotels() throws NoHotelsException;

    HotelResponseListDTO availableHotels(HotelAvailableRequestDTO request) throws ParseException, DestinationException, DateFromException, DateToException, NoHotelsAvailablesException;

    HotelBookingResponseDTO bookHotel(HotelBookingRequestDTO request) throws HotelBookingException, ParseException, PeopleRoomException, DestinationException, DateFromException, DateToException;

    HotelResponseDTO hotelToDTO(Hotel hotel);

    boolean isHotelAvailable(Hotel hotel, Date dateFrom, Date dateTo);

    Hotel getHotelByCode(String hotelCode);

    void checkDatesAndDestination(HotelAvailableRequestDTO request) throws ParseException, DestinationException, DateToException, DateFromException;

    void checkHotelBookingDTO(HotelBookingRequestDTO input) throws HotelBookingException, ParseException, PeopleRoomException, DestinationException, DateFromException, DateToException;
}
