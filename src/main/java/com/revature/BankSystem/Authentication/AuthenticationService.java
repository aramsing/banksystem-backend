package com.revature.BankSystem.Authentication;

import com.revature.BankSystem.DTO.LoginDTO;
import com.revature.BankSystem.DTO.RegisterDTO;
import com.revature.BankSystem.Profile.Profile;
import com.revature.BankSystem.Profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import javax.naming.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, ProfileService profileService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
    }

    public Profile register(RegisterDTO registerDTO) {
        int id;

        try {
            id = profileService.lookupProfileIdByUsername(registerDTO.getUsername());
            return profileService.getProfileById(id);
        }

        catch (NoSuchElementException e) {
            return null;
        }
    }

    public Profile login(LoginDTO loginDTO) throws AuthenticationException {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        Profile profile = profileService.getProfileByUsername(username);

        if (profile == null || !passwordEncoder.matches(password, profile.getPassword())) {
            throw new AuthenticationException("Incorrect credentials");
        }

        return profile;
    }
}
