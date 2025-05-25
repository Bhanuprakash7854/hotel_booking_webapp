package com.project.HotelBooking.service.interfaces;

import com.project.HotelBooking.dto.Response;
import com.project.HotelBooking.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomServiceInterface
{
    Response addRoom(MultipartFile photo, String roomType, BigDecimal roomPrice,String description);
    List<String> getAllRoomTypes();
    Response getAllRooms();
    Response deleteRoom(Long roomId);
    Response getRoomById(Long roomId);
    Response updateRoom(Long roomId,MultipartFile photo, String roomType, BigDecimal roomPrice,String description);
    Response getAvailableRoomsByDateAndType(LocalDate checkInDate,LocalDate checkOutDate,String type);
    Response getAllAvailableRooms();
}
