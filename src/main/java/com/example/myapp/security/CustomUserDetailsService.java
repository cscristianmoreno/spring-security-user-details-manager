package com.example.myapp.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.myapp.models.Users;
import com.example.myapp.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;

    public CustomUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = this.userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("El usuario no existe");
        }

        return new CustomUserDetails(user.get());
    }
}
