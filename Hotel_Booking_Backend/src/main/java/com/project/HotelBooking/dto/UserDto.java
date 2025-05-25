package com.project.HotelBooking.dto;

import com.project.HotelBooking.model.Booking;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class UserDto
{
    private Long id;
    private String name;
    private String email;
    private String role;
    private List<BookingDto> bookingList;

    @Override
    public String toString() {
        return "UserDto{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public List<BookingDto> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookingDto> bookingList) {
        this.bookingList = bookingList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
