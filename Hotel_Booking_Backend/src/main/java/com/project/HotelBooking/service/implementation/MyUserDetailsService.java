package com.project.HotelBooking.service.implementation;

import com.project.HotelBooking.exception.OurException;
import com.project.HotelBooking.model.MyUserDetails;
import com.project.HotelBooking.model.User;
import com.project.HotelBooking.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepo.findByEmail(username).orElseThrow(()->new OurException("User not found"));
        if(user==null)
            throw new OurException("user not found");
        return new MyUserDetails(user);
    }
}
