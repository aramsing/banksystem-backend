package com.revature.BankSystem.Authentication;

import com.revature.BankSystem.DTO.AuthResponseDTO;
import com.revature.BankSystem.DTO.LoginDTO;
import com.revature.BankSystem.DTO.RegisterDTO;
import com.revature.BankSystem.Profile.Profile;
import com.revature.BankSystem.Profile.ProfileService;
import com.revature.BankSystem.Security.JwtGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, AuthenticationManager authenticationManager, ProfileService profileService, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    /**
     * This endpoint is used to register a new user.
     * It takes in a RegisterDTO object in the request body and uses the AuthenticationService to register the user.
     * If the user is successfully registered, it returns the newly created user in the response body.
     * If the user is not registered, it returns a 400 bad request response.
     *
     * @param registerDTO a RegisterDTO object containing the user's information
     * @return a ResponseEntity containing the newly created user if the registration is successful, or a 400 bad request
     * response if the registration is not successful
     */
    @PostMapping("/register")
    public ResponseEntity<Profile> register(@RequestBody RegisterDTO registerDTO) {
        Profile profile = authenticationService.register(registerDTO); // if the user is found, return the user

        if (profile != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // return a 400 bad request response if a user is found
        }

        Profile registeredProfile = new Profile(registerDTO.getUsername(), passwordEncoder.encode(registerDTO.getPassword()), registerDTO.getEmail(), registerDTO.getFirstname(), registerDTO.getLastname(), registerDTO.getAddress(), registerDTO.getUserRole()); // create a new Profile object with the information from the RegisterDTO
        Profile newProfile = profileService.createProfile(registeredProfile); // use the ProfileService to save the new user to the database
        return ResponseEntity.status(HttpStatus.CREATED).body(newProfile); // return a ResponseEntity containing the newly created user
    }

    /**
     * This endpoint is used to log in a user.
     * It takes in a LoginDTO object in the request body and uses the AuthenticationService to log in the user.
     * If the user is successfully logged in, it returns an AuthResponseDTO object containing the generated JWT token in the response body.
     * If the user is not logged in, it returns a 401 unauthorized response.
     *
     * @param loginDTO a LoginDTO object containing the username and password.
     * @return a ResponseEntity containing the generated JWT token if the login is successful, or a 401 unauthorized
     * response if the login is not successful.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            Profile profile = authenticationService.login(loginDTO); // if the user is found, return the user

            if (profile == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // return a 401 unauthorized response if a user is not found
            }

            String token = jwtGenerator.generateToken(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()), profile.getId()); // generate a JWT token for the user using the username, password, and id
            AuthResponseDTO authResponseDTO = new AuthResponseDTO();
            authResponseDTO.setAccessToken(token); // sets the JWT token in the AuthResponseDTO object
            authResponseDTO.setTokenType("Bearer"); // sets the token type to "Bearer"
            authResponseDTO.setExpiresIn(3600); // sets the expiration time in seconds
            authResponseDTO.setRefreshToken(token); // sets the refresh token to the same JWT token
            authResponseDTO.setProfile(profile); // sets the profile in the AuthResponseDTO object
            return ResponseEntity.status(HttpStatus.OK).body(authResponseDTO); // return the AuthResponseDTO object containing the JWT token
        }

        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}