package sabre.desafio2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sabre.desafio2.DTOs.HotelAvailableRequestDTO;
import sabre.desafio2.DTOs.HotelBookingRequestDTO;
import sabre.desafio2.DTOs.HotelBookingResponseDTO;
import sabre.desafio2.DTOs.HotelResponseListDTO;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.services.HotelService;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1")
public class HotelController {
    @Autowired
    HotelService hotelService;

    /**
     * GET request to get a list of registered hotels that meet given filters.
     *
     * @param dateFrom    (optional) initial date the hotel must be available, it is used as a filter.
     * @param dateTo      (optional) end date the hotel must be available, it is used as a filter.
     * @param destination (optional) place where the hotel must be located, it is used as a filter.
     * @return If no parameters are specified, returns a list of all registered hotels,
     * If parameters are specified, returns a list of registered hotels that meet this filter.
     */
    @GetMapping("/hotels")
    public ResponseEntity<HotelResponseListDTO> getHotels(@RequestParam(required = false) String dateFrom,
                                                          @RequestParam(required = false) String dateTo,
                                                          @RequestParam(required = false) String destination)
            throws DestinationException, DateFromException, ParseException, DateToException, NoHotelsException, NoHotelsAvailablesException {
        if (dateFrom == null & dateTo == null & destination == null)
            return new ResponseEntity<>(hotelService.getHotels(), HttpStatus.OK);
        HotelAvailableRequestDTO data = new HotelAvailableRequestDTO(dateFrom, dateTo, destination);
        return new ResponseEntity<>(hotelService.availableHotels(data), HttpStatus.OK);
    }

    /**
     * POST request to make a reservation for a specific hotel.
     *
     * @param request payload that contains the information to make the reservation.
     * @return response that contains the input data to make the reservation and the transaction result.
     */
    @PostMapping("/booking")
    public ResponseEntity<HotelBookingResponseDTO> bookHotel(@RequestBody HotelBookingRequestDTO request)
            throws HotelBookingException, ParseException, PeopleRoomException, DestinationException, DateFromException, DateToException {
        return new ResponseEntity<>(hotelService.bookHotel(request), HttpStatus.OK);
    }
}