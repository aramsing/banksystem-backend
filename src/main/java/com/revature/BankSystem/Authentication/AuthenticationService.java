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

    /**
     * This method registers a user with the given information.
     * It takes a RegisterDTO as input and returns a Profile object if the profile is found within the database.
     * If a profile is found, the method returns the profile.
     * The method first looks for the user in the database. If the user is found, the method returns the user.
     * If the user is not found, the method returns null meaning that we can register the user.
     *
     * @param registerDTO The RegisterDTO object containing the information of the user to be registered.
     * @return The Profile object of the user if the registration is successful, or null if the registration is not successful.
     */
    public Profile register(RegisterDTO registerDTO) {
        int id;

        try {
            id = profileService.lookupProfileIdByUsername(registerDTO.getUsername()); // if the user is found, return the user
            return profileService.getProfileById(id);
        }

        catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * This method logs in a user with the given username and password.
     * It takes a LoginDTO as input and returns a Profile object if the login is successful.
     * If the login is not successful, it throws an AuthenticationException.
     *
     * @param loginDTO The LoginDTO object containing the username and password.
     * @return The Profile object of the user if the login is successful.
     * @throws AuthenticationException If the login is not successful.
     */
    public Profile login(LoginDTO loginDTO) throws AuthenticationException {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        Profile profile = profileService.getProfileByUsername(username); // if the user is found, return the user

        if (profile == null || !passwordEncoder.matches(password, profile.getPassword())) {
            throw new AuthenticationException("Incorrect credentials");
        }

        return profile;
    }
}