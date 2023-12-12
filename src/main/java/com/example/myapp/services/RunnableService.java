package com.example.myapp.services;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.example.myapp.models.Authority;
import com.example.myapp.repository.AuthorityRepository;
import com.example.myapp.roles.Role;

@Service
public class RunnableService implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    public RunnableService(final AuthorityRepository authorityRepository) {
            this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) {

        int num = 1;

        for (Role role: Role.values()) {
            Optional<Authority> authorityExists = this.authorityRepository.findById(num);
            
            if (authorityExists.isPresent()) {
                return;
            }
            
            Authority authority = new Authority(role);
            authorityRepository.save(authority);
            num++;
        }
    }
}
