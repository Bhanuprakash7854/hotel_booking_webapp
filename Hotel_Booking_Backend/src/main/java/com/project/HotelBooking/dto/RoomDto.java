package com.project.HotelBooking.dto;

import com.project.HotelBooking.model.Booking;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomDto
{
    private long id;
    private String roomType;
    private BigDecimal roomPrice;
    private byte[] roomPhoto;
    private String description;
    private List<BookingDto> bookingList;


    public List<BookingDto> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookingDto> bookingList) {
        this.bookingList = bookingList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getRoomPhoto() {
        return roomPhoto;
    }

    public void setRoomPhoto(byte[] roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

    public BigDecimal getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(BigDecimal roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
