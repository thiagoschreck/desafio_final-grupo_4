package sabre.desafio2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sabre.desafio2.models.DTOs.*;
import sabre.desafio2.models.entities.Hotel;
import sabre.desafio2.exceptions.InvalidDateRangeException;
import sabre.desafio2.exceptions.InvalidDestinationException;
import sabre.desafio2.exceptions.InvalidRoomTypeException;
import sabre.desafio2.exceptions.NoHotelsException;
import sabre.desafio2.repositories.IHotelRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HotelService {
    @Autowired
    IHotelRepository hotelRepository;

    public HotelService(IHotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    // ALTAS

    public StatusDTO createHotel(HotelDTO hotel) {
        // Flight newFlight = dtoToFlight(flight);
        // todo - add flight to db
        return new StatusDTO("Vuelo dado de alta correctamente");
    }

    public StatusDTO createBooking(HotelBookingRequestDTO request)
    throws ParseException, InvalidDestinationException, InvalidRoomTypeException, InvalidDateRangeException {
        checkHotelBookingDTO(request);
        // todo - add booking to db
        return new StatusDTO("Reserva de vuelo dada de alta correctamente");
    }

    // MODIFICACIONES

    public StatusDTO updateHotel(String hotelCode, HotelDTO hotelDTO) throws Exception {
        // todo - invalid flightNumber exception
        // throw new Exception();
        // Flight flight = dtoToFlight(flightDTO);
        // todo - update flight to db
        return new StatusDTO("Vuelo modificado correctamente");
    }

    public StatusDTO updateBooking(String id, FlightBookingRequestDTO request) throws Exception {
        // todo - invalid id exception
        // throw new Exception();
        // todo - update reservation to db
        return new StatusDTO("Reserva de vuelo modificada correctamente");
    }

    // CONSULTAS

    public List<HotelDTO> getHotels() throws NoHotelsException {
        // todo - get list of Hotels
        List<HotelDTO> hotels = new ArrayList<>();
        // todo - convert hotels into DTOs
        return hotels;
    }

    public List<HotelDTO> availableHotels(HotelAvailableRequestDTO request)
    throws ParseException, InvalidDestinationException, InvalidDateRangeException {
        checkDatesAndDestination(request);
        // todo - get list of hotels
        // todo - filter
        List<HotelDTO> hotels = new ArrayList<>();
        // todo - convert hotels into DTOs
        return hotels;
    }

    public List<HotelBookingResponseDTO> getBookings() {
        // todo - get list of Bookings
        // todo - filter
        List<HotelBookingResponseDTO> bookings = new ArrayList<>();
        // todo - convert bookings into DTOs
        return bookings;
    }

    // BAJAS

    public StatusDTO deleteHotel(String hotelCode) {
        // todo - check existence
        // todo - delete hotel from db
        return new StatusDTO("Hotel dado de baja correctamente");
    }

    public StatusDTO deleteBooking(String id) {
        // todo - check existence
        // todo - delete booking from db
        return new StatusDTO("Hotel de vuelo dada de baja correctamente");
    }

    // AUX FUNCTIONS

    /**
     * converts a Hotel entity to a DTO.
     *
     * @param hotel entity to convert.
     * @return a HotelDTO containing the data of the given Hotel entity.
     */
    public HotelDTO hotelToDTO(Hotel hotel) {
        return new HotelDTO(hotel.getHotelCode(),
                hotel.getName(),
                hotel.getPlace(),
                hotel.getRoomType(),
                hotel.getRoomPrice(),
                hotel.getDisponibilityDateFrom(),
                hotel.getDisponibilityDateTo(),
                hotel.isBooked());
    }

    /**
     * converts a Hotel DTO to a Hotel entity.
     *
     * @param hotel entity to convert.
     * @return a HotelDTO containing the data of the given Hotel entity.
     */
    public Hotel dtoToHotel(HotelDTO hotel) {
        return new Hotel(hotel.getHotelCode(),
                hotel.getName(),
                hotel.getPlace(),
                hotel.getRoomType(),
                hotel.getRoomPrice(),
                hotel.getDisponibilityDateFrom(),
                hotel.getDisponibilityDateTo(),
                hotel.isBooking(), null);
    }

    /**
     * aux method to check input of availableHotels method.
     *
     * @param request DTO to check, contains the filters to apply (dateFrom, dateTo, destination).
     */
    public void checkDatesAndDestination(HotelAvailableRequestDTO request)
            throws ParseException, InvalidDateRangeException, InvalidDestinationException {
        // date validation
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateFrom());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateTo());
        if (dateF.after(dateT))
            throw new InvalidDateRangeException();
        // destination validation
        if (request.getDestination() == null || request.getDestination().isEmpty())
            throw new InvalidDestinationException();
        boolean existDestination = false;
//        for (Hotel hotel : hotelRepository.getHotels())
//            if (hotel.getPlace().toUpperCase(Locale.ROOT).equals(request.getDestination().toUpperCase(Locale.ROOT))) {
//                existDestination = true;
//                break;
//            }
        if (!existDestination)
            throw new InvalidDestinationException();
    }

    /**
     * aux method to check input of bookHotel method.
     *
     * @param input DTO to check, contains the data to make the reservation.
     */
    public void checkHotelBookingDTO(HotelBookingRequestDTO input)
            throws ParseException, InvalidRoomTypeException, InvalidDestinationException, InvalidDateRangeException {
        HotelBookingDTO booking = input.getBooking();
        HotelAvailableRequestDTO request = new HotelAvailableRequestDTO(booking.getDateFrom(), booking.getDateTo(), booking.getDestination());
        // date and destination validation
        checkDatesAndDestination(request);
        // roomType validation
        if ((booking.getPeopleAmount() != 1 && booking.getRoomType().equalsIgnoreCase("SINGLE")) ||
                (booking.getPeopleAmount() != 2 && booking.getRoomType().equalsIgnoreCase("DOBLE")) ||
                (booking.getPeopleAmount() != 3 && booking.getRoomType().equalsIgnoreCase("TRIPLE")) ||
                (booking.getPeopleAmount() < 4 && booking.getRoomType().equalsIgnoreCase("MÚLTIPLE"))) {
            throw new InvalidRoomTypeException();
        }
    }
}
