package com.project.HotelBooking.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nonnull
    private LocalDate checkInDate;
    @Future
    private LocalDate checkOutDate;
    @Min(value = 1,message = "Must be greater than zero")
    private int noOfAdults;
    @Min(value = 1,message = "Must not less than zero")
    private int noOfChildren;
    private int totalNoOfGuests;
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public Booking(){}

    public void calculateTotalNoOfGuests()
    {
        totalNoOfGuests=noOfAdults+noOfChildren;
    }

    public void setNoOfAdults(@Min(value = 1, message = "Must be greater than zero") int noOfAdults) {
        this.noOfAdults = noOfAdults;
        calculateTotalNoOfGuests();
    }

    public void setNoOfChildren(@Min(value = 1, message = "Must not less than zero") int noOfChildren) {
        this.noOfChildren = noOfChildren;
        calculateTotalNoOfGuests();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                ", id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", noOfAdults=" + noOfAdults +
                ", noOfChildren=" + noOfChildren +
                ", totalNoOfGuests=" + totalNoOfGuests +
                '}';
    }


    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    @Nonnull
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(@Nonnull LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public @Future LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(@Future LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Min(value = 1, message = "Must be greater than zero")
    public int getNoOfAdults() {
        return noOfAdults;
    }

    @Min(value = 1, message = "Must not less than zero")
    public int getNoOfChildren() {
        return noOfChildren;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getTotalNoOfGuests() {
        return totalNoOfGuests;
    }

    public void setTotalNoOfGuests(int totalNoOfGuests) {
        this.totalNoOfGuests = totalNoOfGuests;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
