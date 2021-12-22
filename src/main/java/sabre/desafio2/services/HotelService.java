package sabre.desafio2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.models.dtos.Flight.FlightDTO;
import sabre.desafio2.models.dtos.Hotel.HotelDTO;
import sabre.desafio2.models.dtos.Hotel.HotelRequestDTO;
import sabre.desafio2.models.dtos.Shared.StatusDTO;
import sabre.desafio2.models.entities.Flight;
import sabre.desafio2.models.entities.Hotel;
import sabre.desafio2.repositories.IBookingRepository;
import sabre.desafio2.repositories.IHotelRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {
    @Autowired
    IHotelRepository hotelRepository;
    @Autowired
    IBookingRepository bookingsRepository;
    @Autowired
    SharedService sharedService;

    public HotelService(IHotelRepository hotelRepository,
                        IBookingRepository bookingsRepository,
                        SharedService sharedService) {
        this.hotelRepository = hotelRepository;
        this.bookingsRepository = bookingsRepository;
        this.sharedService = sharedService;
    }

    public StatusDTO createBooking(HotelRequestDTO request)
            throws ParseException, InvalidDateRangeException {
        HotelDTO hotel = new HotelDTO(request);
        if (hotel.getAvailableFrom().after(hotel.getAvailableTo()))
            throw new InvalidDateRangeException();
        hotelRepository.save(sharedService.mapper.map(hotel, Hotel.class));
        return new StatusDTO("Hotel booked successfully");
    }

    public StatusDTO updateHotel(String hotelCode, HotelRequestDTO request)
            throws Exception, InvalidDateRangeException {
        Hotel currentHotel = hotelRepository.findById(hotelCode).get();
        if (currentHotel == null)
            throw new NoHotelsFoundException();
        HotelDTO newHotel = new HotelDTO(request);
        if (newHotel.getAvailableFrom().after(newHotel.getAvailableTo()))
            throw new InvalidDateRangeException();
        hotelRepository.save(sharedService.mapper.map(newHotel, Hotel.class));
        return new StatusDTO("Hotel modified successfully");
    }

    public StatusDTO deleteHotel(String hotelCode) throws NoHotelsException {
        if (!hotelRepository.existsById(hotelCode))
            throw new NoHotelsException();
        hotelRepository.deleteById(hotelCode);
        return new StatusDTO("Hotel removed successfully");
    }

    public List<HotelDTO> getHotels() throws NoHotelsException {
        List<Hotel> hotelList = hotelRepository.findAll();
        if (hotelList.isEmpty())
            throw new NoHotelsException();
        return hotelList.stream().map(hotel -> sharedService.mapper.map(hotel, HotelDTO.class)).collect(Collectors.toList());
    }

    public List<HotelDTO> availableHotels(String dateFrom,
                                          String dateTo,
                                          String place)
            throws ParseException, InvalidDateRangeException, NoHotelsFoundException, NoHotelsException {
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(dateFrom);
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(dateTo);
        if (dateF.after(dateT))
            throw new InvalidDateRangeException();
        List<HotelDTO> hotels = new ArrayList<>();
        for (HotelDTO hotel : getHotels())
            if (hotel.getAvailableFrom().compareTo(dateF) == 0 ||
                    hotel.getAvailableTo().compareTo(dateT) == 0 ||
                    hotel.getPlace().equalsIgnoreCase(place))
                hotels.add(hotel);
        if (hotels.isEmpty())
            throw new NoHotelsFoundException();
        return hotels;
    }
/*
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

 */
}
