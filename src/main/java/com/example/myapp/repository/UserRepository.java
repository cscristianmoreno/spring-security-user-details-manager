package com.example.myapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.myapp.models.Users;

public interface UserRepository extends CrudRepository<Users, Integer> {
    List<Users> findAllById(int id);

    Optional<Users> findByEmail(String email);
}
