package sabre.desafio2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sabre.desafio2.DTOs.*;
import sabre.desafio2.entities.Hotel;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.repositories.HotelRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
public class HotelService implements IHotelService {
    @Autowired
    HotelRepository hotelRepository = new HotelRepository();

    /**
     * gets a list of all registered hotels.
     *
     * @return a DTO containing a list of all registered hotels.
     */
    public HotelResponseListDTO getHotels() throws NoHotelsException {
        HotelResponseListDTO listDTO = new HotelResponseListDTO();
        for (Hotel hotel : hotelRepository.getHotels()) {
            listDTO.getHotels().add(hotelToDTO(hotel));
        }
        if (listDTO.getHotels().isEmpty())
            throw new NoHotelsException();
        return listDTO;
    }

    /**
     * gets a list of all registered hotels that meet the filters passed as a parameter.
     *
     * @param request DTO containing the filters to apply (dateFrom, dateTo, destination).
     * @return a DTO containing a list of all registered hotels that meet the given filters.
     */
    public HotelResponseListDTO availableHotels(HotelAvailableRequestDTO request)
            throws DestinationException, DateFromException, ParseException, DateToException, NoHotelsAvailablesException {
        checkDatesAndDestination(request);
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateFrom());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateTo());
        HotelResponseListDTO listDTO = new HotelResponseListDTO();
        for (Hotel hotel : hotelRepository.getHotels()) {
            if (hotel.getPlace().equals(request.getDestination()) && isHotelAvailable(hotel, dateF, dateT))
                listDTO.getHotels().add(hotelToDTO(hotel));
        }
        if (listDTO.getHotels().isEmpty())
            throw new NoHotelsAvailablesException();
        return listDTO;
    }

    /**
     * make a hotel reservation with the data passed as a parameter.
     *
     * @param request DTO containing the data to make the reservation.
     * @return a DTO containing the input given for the reservation, the price and the status of the transaction.
     */
    public HotelBookingResponseDTO bookHotel(HotelBookingRequestDTO request)
            throws HotelBookingException, ParseException, PeopleRoomException, DestinationException, DateFromException, DateToException {
        double interest = calculateInterest(request.getBooking().getPaymentMethod());
        checkHotelBookingDTO(request);
        HotelBookingInternalResponseDTO booking = createInternalBooking(request);
        Hotel hotel = getHotelByCode(booking.getHotelCode());
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(booking.getDateFrom());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(booking.getDateTo());
        int days = (int) TimeUnit.MILLISECONDS.toDays(Math.abs(dateT.getTime() - dateF.getTime()));
        if (getHotelByCode(hotel.getHotelCode()) == null)
            throw new HotelBookingException(request, "Transaction failure, hotel not registered");
        if (!isHotelAvailable(hotel, dateF, dateT))
            throw new HotelBookingException(request, "Transaction failure, hotel not available for given dates");
        HotelBookingResponseDTO response = new HotelBookingResponseDTO();
        response.setUserName(request.getUserName());
        response.setAmount(hotel.getRoomPrice() * days);
        response.setInterest(interest);
        response.setTotal(response.getAmount() + response.getAmount() * (interest / 100));
        response.setBooking(booking);
        hotel.setDisponibilityDateFrom(dateT);
        hotelRepository.createReservation(hotel);
        response.setStatusCode(new StatusDTO("Transaction completed successfully"));
        return response;
    }

    /**
     * @param request
     * @return
     */
    private HotelBookingInternalResponseDTO createInternalBooking(HotelBookingRequestDTO request) {
        HotelBookingInternalResponseDTO booking = new HotelBookingInternalResponseDTO();
        booking.setDateFrom(request.getBooking().getDateFrom());
        booking.setDateTo(request.getBooking().getDateTo());
        booking.setDestination(request.getBooking().getDestination());
        booking.setHotelCode(request.getBooking().getHotelCode());
        booking.setPeopleAmount(request.getBooking().getPeopleAmount());
        booking.setRoomType(request.getBooking().getRoomType());
        booking.setPeople(request.getBooking().getPeople());
        return booking;
    }

    /**
     * calculates the interest of a transaction
     *
     * @param payment
     * @return
     */
    private double calculateInterest(PaymentMethodDTO payment) {
        double interest = 0;
        if (payment.getType().equalsIgnoreCase("Credit")) {
            if (payment.getDues() < 4)
                interest = 5;
            else
                interest = 10;
        }
        return interest;
    }

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
     * check that a specific hotel is available.
     *
     * @param hotel    hotel to check if it is available.
     * @param dateFrom from which date the hotel must be available.
     * @param dateTo   until which date the hotel must be available.
     * @return true in case the hotel fulfill all the given parameters (dateFrom, dateTo).
     * otherwise, it returns false.
     */
    public boolean isHotelAvailable(Hotel hotel, Date dateFrom, Date dateTo) {
        return hotel.getDisponibilityDateFrom().compareTo(dateFrom) <= 0 &&
                hotel.getDisponibilityDateTo().compareTo(dateTo) >= 0;
    }

    /**
     * get a Hotel instance from a given hotel code.
     *
     * @param hotelCode hotel code.
     * @return If there is an instance with the hotel code, it returns the instance.
     * otherwise, it returns null.
     */
    public Hotel getHotelByCode(String hotelCode) {
        for (Hotel hotel : hotelRepository.getHotels()) {
            if (hotel.getHotelCode().equals(hotelCode)) {
                return hotel;
            }
        }
        return null;
    }


    /**
     * aux method to check input of availableHotels method.
     *
     * @param request DTO to check, contains the filters to apply (dateFrom, dateTo, destination).
     */
    public void checkDatesAndDestination(HotelAvailableRequestDTO request)
            throws DestinationException, DateToException, DateFromException, ParseException {
        // date validation
        Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateFrom());
        Date dateT = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateTo());
        if (dateF.after(dateT))
            throw new DateFromException();
        // destination validation
        if (request.getDestination() == null || request.getDestination().isEmpty())
            throw new DestinationException();
        boolean existDestination = false;
        for (Hotel hotel : hotelRepository.getHotels())
            if (hotel.getPlace().toUpperCase(Locale.ROOT).equals(request.getDestination().toUpperCase(Locale.ROOT))) {
                existDestination = true;
                break;
            }
        if (!existDestination)
            throw new DestinationException();
    }

    /**
     * aux method to check input of bookHotel method.
     *
     * @param input DTO to check, contains the data to make the reservation.
     */
    public void checkHotelBookingDTO(HotelBookingRequestDTO input)
            throws PeopleRoomException, DestinationException, DateFromException, ParseException, DateToException {
        HotelBookingDTO booking = input.getBooking();
        HotelAvailableRequestDTO request = new HotelAvailableRequestDTO(booking.getDateFrom(), booking.getDateTo(), booking.getDestination());
        // date and destination validation
        checkDatesAndDestination(request);
        // roomType validation
        if ((booking.getPeopleAmount() != 1 && booking.getRoomType().equalsIgnoreCase("SINGLE")) ||
                (booking.getPeopleAmount() != 2 && booking.getRoomType().equalsIgnoreCase("DOBLE")) ||
                (booking.getPeopleAmount() != 3 && booking.getRoomType().equalsIgnoreCase("TRIPLE")) ||
                (booking.getPeopleAmount() < 4 && booking.getRoomType().equalsIgnoreCase("MÚLTIPLE"))) {
            throw new PeopleRoomException();
        }
    }

    public StatusDTO createHotel(HotelDTO hotel) {
        Hotel newHotel = dtoToHotel(hotel);
        // todo - add hotel to db
        return new StatusDTO("Hotel dado de alta/baja/modificado correctamente");
    }

    public StatusDTO modifyHotel(String hotelCode, HotelDTO hotel) {
        Hotel newHotel = getHotelByCode(hotelCode);
        if (newHotel == null) {
            createHotel(hotel);
        } else {
            if (!(hotel.getName().equals(newHotel.getName()))) {
                newHotel.setName(hotel.getName());
            }
            if (!(hotel.getPlace().equals(newHotel.getPlace()))) {
                newHotel.setPlace(hotel.getPlace());
            }
            if (!(hotel.getRoomType().equals(newHotel.getRoomType()))) {
                newHotel.setRoomType(hotel.getRoomType());
            }
            if (!(hotel.getRoomPrice() == newHotel.getRoomPrice())) {
                newHotel.setRoomPrice(hotel.getRoomPrice());
            }
            if (!(hotel.getDisponibilityDateFrom().equals(newHotel.getDisponibilityDateFrom()))) {
                newHotel.setDisponibilityDateFrom(hotel.getDisponibilityDateFrom());
            }
            if (!(hotel.getDisponibilityDateTo().equals(newHotel.getDisponibilityDateTo()))) {
                newHotel.setDisponibilityDateTo(hotel.getDisponibilityDateTo());
            }
        }
        return new StatusDTO("Hotel dado de alta/baja/modificado correctamente");

    }

//Deletee
}
