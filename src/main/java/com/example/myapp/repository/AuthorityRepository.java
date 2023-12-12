package com.example.myapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.myapp.models.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Integer> {
    
}
