package com.project.HotelBooking.controller;

import com.project.HotelBooking.dto.BookingDto;
import com.project.HotelBooking.model.Booking;
import com.project.HotelBooking.model.Room;
import com.project.HotelBooking.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class TestController
{

    @Autowired
    private RoomRepo repo;

    @GetMapping("/room")
    public String getRooms(@RequestParam LocalDate checkInDate,@RequestParam LocalDate checkOutDate,@RequestParam String roomType)
    {
        return repo.findAvailableRoomsByDatesAndTypes(checkInDate,checkOutDate,roomType).toString();
    }

    @GetMapping("/test")
    public String test()
    {
        return "tested";
    }
}

