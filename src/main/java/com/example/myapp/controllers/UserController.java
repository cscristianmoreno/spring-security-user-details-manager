package com.example.myapp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myapp.dto.UsersDTO;
import com.example.myapp.exceptions.InvalidException;
import com.example.myapp.models.Users;
import com.example.myapp.repository.UserRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@ResponseBody
@RequestMapping(path = "/users")
public class UserController {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(final UserRepository userRepository, final ModelMapper modelMapper, 
    final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/all")
    public List<UsersDTO> getUsers() {
        List<Users> users = (List<Users>) this.userRepository.findAll();
        List<UsersDTO> usersDTO = users.stream().map((user) -> modelMapper.map(user, UsersDTO.class)).collect(Collectors.toList());
        return usersDTO;
    }    

    @PostMapping("/save")
    public ResponseEntity<Integer> saveUser(@RequestBody Users user) throws InvalidException {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        }
        catch (Exception e) {
            throw new InvalidException(e.getMessage());
        }
    }

    @PostMapping("/admin")
    @PostAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAdmin() {
        return "Ingresó como administrador";
    }
    
    @PostMapping("/user")
    @PostAuthorize("hasAuthority('ROLE_USER')")
    public String getUser() {
        return "Ingresó como usuario";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable int id) throws InvalidException {
        try {
            this.userRepository.deleteById(id);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InvalidException("Ocurrió un error");
        }
    }
}
