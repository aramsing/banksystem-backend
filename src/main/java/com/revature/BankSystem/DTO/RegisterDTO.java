package com.revature.BankSystem.DTO;

import com.revature.BankSystem.Profile.Profile;
import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private Profile.UserRole userRole;
}