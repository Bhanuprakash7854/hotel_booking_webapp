package com.project.HotelBooking.controller;


import com.project.HotelBooking.dto.LoginRequest;
import com.project.HotelBooking.dto.Response;
import com.project.HotelBooking.model.User;
import com.project.HotelBooking.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private UserServiceInterface userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user)
    {
        Response response = userService.register(user);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest)
    {
        Response response = userService.login(loginRequest);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
