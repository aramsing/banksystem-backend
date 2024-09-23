package com.revature.BankSystem.DTO;

import com.revature.BankSystem.Profile.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponseDTO {
    private String accessToken;

    private String tokenType;

    private int expiresIn;

    private String refreshToken;

    private Profile profile;
}