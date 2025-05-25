package com.project.HotelBooking.service.interfaces;

import com.project.HotelBooking.dto.LoginRequest;
import com.project.HotelBooking.dto.Response;
import com.project.HotelBooking.model.User;

public interface UserServiceInterface
{
    Response register(User user);
    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUserBookingHistory(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getMyInfo(String email);
}
