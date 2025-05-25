package com.project.HotelBooking.service.implementation;

import com.project.HotelBooking.Utils.Utils;
import com.project.HotelBooking.dto.BookingDto;
import com.project.HotelBooking.dto.Response;
import com.project.HotelBooking.exception.OurException;
import com.project.HotelBooking.model.Booking;
import com.project.HotelBooking.model.Room;
import com.project.HotelBooking.model.User;
import com.project.HotelBooking.repository.BookingRepo;
import com.project.HotelBooking.repository.RoomRepo;
import com.project.HotelBooking.repository.UserRepo;
import com.project.HotelBooking.service.interfaces.BookingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookingService implements BookingServiceInterface
{

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Response saveBooking(Long roomId, Long userId, Booking booking) {
        Response response = new Response();
        try
        {
            if(booking.getCheckOutDate().isBefore(booking.getCheckInDate()))
            {
                throw new IllegalArgumentException("Check in date must come after check out date");
            }
            Room room = roomRepo.findById(roomId).orElseThrow(()->new OurException("Room not found"));
            User user = userRepo.findById(userId).orElseThrow(()->new OurException("User not found"));

            List<Booking> bookingList = room.getBookingList();
            if(!isRoomAvailable(bookingList,booking))
            {
                throw new OurException("Room not available for the selected date range");
            }
            String confirmationCode = Utils.generateRandomString(10);
            booking.setBookingConfirmationCode(confirmationCode);
            booking.setRoom(room);
            booking.setUser(user);
            bookingRepo.save(booking);
            response.setStatusCode(200);
            response.setMessage("Booking sucessful");
            response.setBookingConfirmationCode(confirmationCode);
        }catch (OurException e)
        {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error saving booking "+e.getMessage());
        }

        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode)
    {
        Response response = new Response();
        try
        {
            Booking booking = bookingRepo.findByBookingConfirmationCode(confirmationCode).orElseThrow(()->new OurException("Booking not found"));
            BookingDto bookingDto = Utils.mapBookingEntityToBookingDto(booking);
            response.setBooking(bookingDto);
            response.setStatusCode(200);
            response.setMessage("Sucessful");
        }catch (OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e)
        {
            response.setMessage("Error finding Booking "+e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response findAllBookings()
    {
        Response response = new Response();
        try
        {
            List<Booking> bookingList = bookingRepo.findAll();
            List<BookingDto> bookingDtoList = Utils.mapBookingListToBookingDtoList(bookingList);
            response.setStatusCode(200);
            response.setMessage("Sucessful");
            response.setBookingList(bookingDtoList);
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error finding bookings "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId)
    {
        Response response = new Response();
        try
        {
            Booking booking = bookingRepo.findById(bookingId).orElseThrow(()->new OurException("Booking not found"));
            bookingRepo.deleteById(bookingId);
            response.setMessage("Sucessful");
            response.setStatusCode(200);
        }catch (OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error canceling booking "+e.getMessage());
        }
        return response;
    }

    private boolean isRoomAvailable(List<Booking> bookingList, Booking booking)
    {
        return bookingList.stream().noneMatch(existingBooking -> !(booking.getCheckOutDate().isBefore(existingBooking.getCheckInDate()) ||
                booking.getCheckInDate().isAfter(booking.getCheckOutDate())));
    }
}
