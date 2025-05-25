package com.project.HotelBooking.service.implementation;

import com.project.HotelBooking.Utils.Utils;
import com.project.HotelBooking.dto.Response;
import com.project.HotelBooking.dto.RoomDto;
import com.project.HotelBooking.exception.OurException;
import com.project.HotelBooking.model.Room;
import com.project.HotelBooking.repository.RoomRepo;
import com.project.HotelBooking.service.interfaces.RoomServiceInterface;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements RoomServiceInterface
{

    @Autowired
    private RoomRepo roomRepo;

    @Override
    public Response addRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description)
    {
        Response response = new Response();
        try
        {

            if (photo == null || photo.isEmpty() || roomType == null || roomType.isBlank() || roomPrice == null || description.isBlank()) {
                response.setStatusCode(400);
                response.setMessage("Please provide values for all fields(photo, roomType,roomPrice)");
                return response;
            }
            Room room = new Room();
            room.setRoomPhoto(photo.getBytes());
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setDescription(description);
            roomRepo.save(room);
            RoomDto roomDto = Utils.mapRoomEntityToRoomDto(room);
            response.setMessage("Sucessful");
            response.setStatusCode(200);
            response.setRoom(roomDto);
        }catch (IOException e)
        {
            response.setStatusCode(400);
            response.setMessage("Image is required "+e.getMessage());
        }
        catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error adding room "+e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepo.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try
        {
            List<Room> rooms = roomRepo.findAll();
            List<RoomDto> roomDtos = Utils.mapRoomListToRoomDtoList(rooms);
            response.setMessage("Sucessful");
            response.setStatusCode(200);
            response.setRoomList(roomDtos);
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error finding rooms "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();
        try {
            Room room = roomRepo.findById(roomId).orElseThrow(()->new OurException("Room not found"));
            roomRepo.delete(room);
            response.setMessage("Sucessful");
            response.setStatusCode(200);
        }catch (OurException e)
        {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error deleting room "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();
        try {
            Room room = roomRepo.findById(roomId).orElseThrow(()->new OurException("Room not found"));
            RoomDto roomDto = Utils.mapRoomEntityToRoomDto(room);
            response .setStatusCode(200);
            response.setMessage("Sucessful");;
            response.setRoom(roomDto);
        }catch (OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error finding room "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();
        try {
            Room room = roomRepo.findById(roomId).orElseThrow(()->new OurException("Room not Found"));
            if(roomType!=null)room.setRoomType(roomType);
            if(roomPrice!=null)room.setRoomPrice(roomPrice);
            if(description!=null)room.setDescription(description);
            if(photo!=null)room.setRoomPhoto(photo.getBytes());
            roomRepo.save(room);
            RoomDto roomDto = Utils.mapRoomEntityToRoomDto(room);
            response.setStatusCode(200);
            response.setMessage("Sucessful");
            response.setRoom(roomDto);
        }catch (OurException e)
        {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error updating room "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String type) {
        Response response = new Response();
        if (checkInDate == null || type == null || type.isBlank() || checkOutDate == null) {
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(checkInDate, roomType,checkOutDate)");
            return response;
        }
        try {
            List<Room> availableRooms = roomRepo.findAvailableRoomsByDatesAndTypes(checkInDate,checkOutDate,type);
            System.out.println("fetched" + availableRooms);
            List<RoomDto> availbleRoomDtos = Utils.mapRoomListToRoomDtoList(availableRooms);
            response.setStatusCode(200);
            response.setMessage("Sucessful");
            response.setRoomList(availbleRoomDtos);
        }catch (Exception e)
        {
            response.setMessage("Error fetching rooms "+e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();
        try {
            List<Room> availableRooms = roomRepo.getAvailableRooms();
            List<RoomDto> availableRoomDtos = Utils.mapRoomListToRoomDtoList(availableRooms);
            response.setStatusCode(200);
            response.setMessage("Sucessful");
            response.setRoomList(availableRoomDtos);
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error fetching rooms "+e.getMessage());
        }
        return response;
    }


}
