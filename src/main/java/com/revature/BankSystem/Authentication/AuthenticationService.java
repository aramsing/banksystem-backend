package com.revature.BankSystem.Authentication;

import com.revature.BankSystem.DTO.LoginDTO;
import com.revature.BankSystem.DTO.RegisterDTO;
import com.revature.BankSystem.Profile.Profile;
import com.revature.BankSystem.Profile.ProfileService;
import org.h2.security.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        int id = profileService.lookupProfileIdByUsername(loginDTO.getUsername());
        Profile profile = profileService.getProfileById(id);

        if (passwordEncoder.matches(loginDTO.getPassword(), profile.getPassword())) {
            return profile;
        }

        else {
            throw new AuthenticationException("Incorrect password");
        }
    }
}
