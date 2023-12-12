package com.example.myapp.dto;


import com.example.myapp.models.Authority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDTO {
    private int id;
    private String name;
    private String lastname;
    private String email;
    private String date;

    private Authority authority;
}
