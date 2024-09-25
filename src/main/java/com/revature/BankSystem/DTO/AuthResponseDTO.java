package com.revature.BankSystem.DTO;

import com.revature.BankSystem.Profile.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DATA TRANSFER OBJECT DOCUMENTATION
 * @author Arjun Ramsinghani
 * The DTO class define objects that carry data between processes in order to reduce the number of method calls.
 * The pattern's main purpose is to reduce roundtrips between the front and back end by batching them together in a single call.
 * A DTO is useful with remote calls as they help reduce the number of calls.
 */

@Data
@NoArgsConstructor
public class AuthResponseDTO {
    private String accessToken;

    private String tokenType;

    private int expiresIn;

    private String refreshToken;

    private Profile profile;
}