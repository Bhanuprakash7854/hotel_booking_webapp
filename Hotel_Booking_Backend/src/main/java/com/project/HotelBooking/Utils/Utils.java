package com.project.HotelBooking.Utils;

import com.project.HotelBooking.dto.BookingDto;
import com.project.HotelBooking.dto.RoomDto;
import com.project.HotelBooking.dto.UserDto;
import com.project.HotelBooking.model.Booking;
import com.project.HotelBooking.model.Room;
import com.project.HotelBooking.model.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils
{
    private static SecureRandom secureRandom = new SecureRandom();
    private static String ALPHANUMERICSTRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString(int length)
    {
        StringBuilder randomString = new StringBuilder();
        for(int i=0;i<length;i++)
        {
            int randomIndex = secureRandom.nextInt(ALPHANUMERICSTRING.length());
            char randChar = ALPHANUMERICSTRING.charAt(randomIndex);
            randomString.append(randChar);
        }
        return randomString.toString();
    }

    public static UserDto mapUserEntityToUserDto(User savedUser)
    {
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setEmail(savedUser.getEmail());
        userDto.setName(savedUser.getName());
        userDto.setRole(savedUser.getRole());

        return userDto;
    }

    public static RoomDto mapRoomEntityToRoomDto(Room room)
    {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomPrice(room.getRoomPrice());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setDescription(room.getDescription());
        roomDto.setRoomPhoto(room.getRoomPhoto());

        return roomDto;
    }

    public static BookingDto mapBookingEntityToBookingDto(Booking booking)
    {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        bookingDto.setCheckInDate(booking.getCheckInDate());
        bookingDto.setCheckOutDate(booking.getCheckOutDate());
        bookingDto.setNoOfAdults(booking.getNoOfAdults());
        bookingDto.setNoOfChildren(booking.getNoOfChildren());
        bookingDto.setTotalNoOfGuests(booking.getTotalNoOfGuests());

        return bookingDto;
    }

    public static UserDto mapUserEntityToUserDtoPlusBookingDto(User user)
    {
        UserDto userDto = mapUserEntityToUserDto(user);
        if(!user.getBookingList().isEmpty())
        {
            userDto.setBookingList(user.getBookingList().stream().map(booking -> mapBookingEntityToBookingDto(booking)).collect(Collectors.toList()));
        }

        return userDto;
    }

    public static RoomDto mapRoomEntityToRoomDtoPlusBookingDto(Room room)
    {
        RoomDto roomDto = mapRoomEntityToRoomDto(room);
        if(!room.getBookingList().isEmpty())
        {
            roomDto.setBookingList(room.getBookingList().stream().map(booking -> mapBookingEntityToBookingDto(booking)).collect(Collectors.toList()));
        }

        return roomDto;
    }

    public static BookingDto mapBookingEntityToBookingDtoPlusRoomDtoAndUserDto(Booking booking,boolean mapUser)
    {
        BookingDto bookingDto = mapBookingEntityToBookingDto(booking);
        if(mapUser)
        {
            bookingDto.setUser(mapUserEntityToUserDto(booking.getUser()));
        }
        if(booking.getRoom() != null)
        {
            bookingDto.setRoom(mapRoomEntityToRoomDto(booking.getRoom()));
        }
        return bookingDto;
    }

    public static List<UserDto> mapUserListToUserDtoList(List<User> users)
    {
        return users.stream().map(user -> mapUserEntityToUserDto(user)).collect(Collectors.toList());
    }

    public static List<RoomDto> mapRoomListToRoomDtoList(List<Room> rooms)
    {
        return rooms.stream().map(room -> mapRoomEntityToRoomDto(room)).collect(Collectors.toList());
    }

    public static List<BookingDto> mapBookingListToBookingDtoList(List<Booking> bookings)
    {
        return bookings.stream().map(booking -> mapBookingEntityToBookingDto(booking)).collect(Collectors.toList());
    }
}


