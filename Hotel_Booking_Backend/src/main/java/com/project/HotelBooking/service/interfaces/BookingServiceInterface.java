package com.project.HotelBooking.service.interfaces;

import com.project.HotelBooking.dto.Response;
import com.project.HotelBooking.model.Booking;

public interface BookingServiceInterface
{
    Response saveBooking(Long roomId, Long userId,Booking booking);
    Response findBookingByConfirmationCode(String confirmationCode);
    Response findAllBookings();
    Response cancelBooking(Long bookingId);
}
