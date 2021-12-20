package sabre.desafio2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sabre.desafio2.DTOs.*;
import sabre.desafio2.exceptions.*;
import sabre.desafio2.services.HotelService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HotelController {
    @Autowired
    HotelService hotelService;

    // ALTAS

    @PostMapping("/hotels/new")
    public ResponseEntity<StatusDTO> newHotel(@RequestBody HotelDTO hotel) {
        return new ResponseEntity<>(hotelService.createHotel(hotel), HttpStatus.OK);
    }

    @PostMapping("/hotel-booking/new")
    public ResponseEntity<StatusDTO> newBooking(@RequestBody HotelBookingRequestDTO request) {
        return new ResponseEntity<>(hotelService.bookHotel(request), HttpStatus.OK);
    }

    // MODIFICACIONES

    @PutMapping("/hotels/edit")
    public ResponseEntity<StatusDTO> editHotel(@RequestParam String hotelCode) {
        return new ResponseEntity<>(hotelService.editHotel(hotelCode), HttpStatus.OK);
    }

    @PutMapping("/hotel-booking/edit")
    public ResponseEntity<StatusDTO> editBooking(@RequestParam String id) {
        return new ResponseEntity<>(hotelService.editBooking(id), HttpStatus.OK);
    }

    // CONSULTAS

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDTO>> getHotels(@RequestParam(required = false) String dateFrom,
                                                          @RequestParam(required = false) String dateTo,
                                                          @RequestParam(required = false) String destination)
            throws DestinationException, DateFromException, ParseException, DateToException, NoHotelsException, NoHotelsAvailablesException {
        if (dateFrom == null & dateTo == null & destination == null)
            return new ResponseEntity<>(hotelService.getHotels(), HttpStatus.OK);
        HotelAvailableRequestDTO data = new HotelAvailableRequestDTO(dateFrom, dateTo, destination);
        return new ResponseEntity<>(hotelService.availableHotels(data), HttpStatus.OK);
    }

    @GetMapping("/hotel-bookings")
    public ResponseEntity<List<HotelBookingResponseDTO>> getBookings() {
        return new ResponseEntity<>(hotelService.getBookings(), HttpStatus.OK);
    }

    // BAJAS

    @DeleteMapping("/hotels/delete")
    public ResponseEntity<StatusDTO> deleteHotel(@RequestParam String hotelCode) {
        return new ResponseEntity<>(hotelService.deleteHotel(hotelCode), HttpStatus.OK);
    }

    @DeleteMapping("/hotel-booking/delete")
    public ResponseEntity<StatusDTO> deleteBooking(@RequestParam String id) {
        return new ResponseEntity<>(hotelService.deleteBooking(id), HttpStatus.OK);
    }
}