package com.project.HotelBooking.controller;

import com.project.HotelBooking.dto.Response;
import com.project.HotelBooking.service.interfaces.RoomServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController
{
    @Autowired
    private RoomServiceInterface roomService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addRoom(
            @RequestParam(value="photo") MultipartFile photo,
            @RequestParam(value="roomType") String roomType,
            @RequestParam(value="roomPrice") BigDecimal roomPrice,
            @RequestParam(value="description") String description
            )
    {
        Response response = roomService.addRoom(photo,roomType,roomPrice,description);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("all")
    public ResponseEntity<Response> getAllRooms()
    {
        Response response = roomService.getAllRooms();
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/types")
    public List<String> getAllRoomTypes()
    {
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/room-by-id/{roomId}")
    public ResponseEntity<Response> getRoomById(@PathVariable Long roomId)
    {
        Response response = roomService.getRoomById(roomId);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/available-rooms-by-date-type")
    public ResponseEntity<Response> getAvailableRoomsByDateAndType(
            @RequestParam(value="checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(value="checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(value="roomType")String roomType
            )
    {
        Response response = roomService.getAvailableRoomsByDateAndType(checkInDate,checkOutDate,roomType);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@PathVariable Long roomId,
                                               @RequestParam(value = "photo", required = false) MultipartFile photo,
                                               @RequestParam(value = "roomType", required = false) String roomType,
                                               @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
                                               @RequestParam(value = "roomDescription", required = false) String roomDescription

    )
    {
        Response response = roomService.updateRoom(roomId,photo,roomType,roomPrice,roomDescription);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long roomId)
    {
        Response response = roomService.deleteRoom(roomId);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
