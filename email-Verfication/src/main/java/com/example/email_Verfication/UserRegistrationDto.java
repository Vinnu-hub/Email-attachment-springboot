package com.example.email_Verfication;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserRegistrationDto {
    private String email;
    private String password;

    // Getters and Setters
}
