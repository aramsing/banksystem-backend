package com.revature.BankSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private String userRole;
}