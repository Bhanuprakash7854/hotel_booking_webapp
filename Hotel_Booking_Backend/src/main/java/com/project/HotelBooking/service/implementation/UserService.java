package com.project.HotelBooking.service.implementation;


import com.project.HotelBooking.Utils.Utils;
import com.project.HotelBooking.dto.LoginRequest;
import com.project.HotelBooking.dto.Response;
import com.project.HotelBooking.dto.UserDto;
import com.project.HotelBooking.exception.OurException;
import com.project.HotelBooking.model.User;
import com.project.HotelBooking.repository.UserRepo;
import com.project.HotelBooking.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface
{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response register(User user)
    {
        Response response = new Response();
        try
        {
            if(user.getRole()==null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if(userRepo.existsByEmail(user.getEmail()))
            {
                throw new OurException(user.getEmail()+" already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepo.save(user);
            UserDto userDto = Utils.mapUserEntityToUserDto(savedUser);
            response.setStatusCode(200);
            response.setUser(userDto);
        }
        catch (OurException e)
        {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Registration " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest)
    {

        Response response = new Response();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new OurException("User not found"));

            String token = jwtService.generateToken(loginRequest.getEmail());
            response.setRole(user.getRole());
            response.setStatusCode(200);
            response.setToken(token);
            response.setMessage("Sucessful");
            response.setExpirationTime("7 days");
        }catch (OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error logging in "+e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<User> users = userRepo.findAll();
            List<UserDto> userDtos = Utils.mapUserListToUserDtoList(users);
            response.setMessage("Sucessful");
            response.setStatusCode(200);
            response.setUserList(userDtos);
        }
        catch (Exception e)
        {
            response.setMessage("Error fetching users "+e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId)
    {
        Response response = new Response();
        try {
            User user = userRepo.findById(Long.parseLong(userId)).orElseThrow(()->new OurException("User not found"));
            UserDto userDto = Utils.mapUserEntityToUserDtoPlusBookingDto(user);
            response.setStatusCode(200);
            response.setMessage("Sucessful");
            response.setUser(userDto);
        }
        catch (OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error fetching user "+e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try {
            userRepo.findById(Long.parseLong(userId)).orElseThrow(()->new OurException("User not found"));
            userRepo.deleteById(Long.parseLong(userId));
            response.setMessage("Sucessful");
            response.setStatusCode(200);
        }catch (OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error deleting user "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        try {
            User user = userRepo.findById(Long.parseLong(userId)).orElseThrow(()->new OurException("User not found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            response.setMessage("Sucessful");
            response.setStatusCode(200);
            response.setUser(userDto);
        }catch (OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e)
        {
            response.setStatusCode(500);
            response.setMessage("Error fetching user "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try{
            User user = userRepo.findByEmail(email).orElseThrow(()-> new OurException("User not found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("Sucessful");
            response.setUser(userDto);
        }catch (OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e)
        {
            response.setMessage("Error fetching user info "+e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }
}
