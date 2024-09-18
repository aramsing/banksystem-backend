package com.revature.BankSystem.Authentication;

import com.revature.BankSystem.DTO.RegisterDTO;
import com.revature.BankSystem.Profile.Profile;
import com.revature.BankSystem.Profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthenticationService {
    private final ProfileService profileService;

    @Autowired
    public AuthenticationService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public Profile register(RegisterDTO registerDTO) {
        int id;

        try {
            id = profileService.lookupProfileIdByEmail(registerDTO.getEmail());
            return profileService.getProfileById(id);
        }

        catch (NoSuchElementException e) {
            return null;
        }
    }
}
