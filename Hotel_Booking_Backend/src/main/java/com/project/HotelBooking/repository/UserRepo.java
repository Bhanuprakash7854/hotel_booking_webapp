package com.project.HotelBooking.repository;

import com.project.HotelBooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User,Long>
{
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
