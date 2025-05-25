package com.project.HotelBooking.controller;

import com.project.HotelBooking.dto.Response;
import com.project.HotelBooking.model.Booking;
import com.project.HotelBooking.service.interfaces.BookingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController
{
    @Autowired
    private BookingServiceInterface bookingService;

    @PostMapping("/book-room/{roomId}/{userId}")
    public ResponseEntity<Response> saveBooking(@PathVariable Long roomId, @PathVariable Long userId, @RequestBody Booking booking)
    {
        Response response = bookingService.saveBooking(roomId,userId,booking);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings()
    {
        Response response = bookingService.findAllBookings();
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode)
    {
        Response response = bookingService.findBookingByConfirmationCode(confirmationCode);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId)
    {
        Response response = bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
