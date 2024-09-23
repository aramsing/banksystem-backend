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

    @PostMapping("/register")
    public ResponseEntity<Profile> register(@RequestBody RegisterDTO registerDTO) {
        Profile profile = authenticationService.register(registerDTO);

        if (profile != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Profile registeredProfile = new Profile(registerDTO.getUsername(), passwordEncoder.encode(registerDTO.getPassword()), registerDTO.getEmail(), registerDTO.getFirstname(), registerDTO.getLastname(), registerDTO.getAddress(), registerDTO.getUserRole());
        Profile newProfile = profileService.createProfile(registeredProfile);
        return ResponseEntity.status(HttpStatus.OK).body(newProfile);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            Profile profile = authenticationService.login(loginDTO);

            if (profile == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            String token = jwtGenerator.generateToken(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()), profile.getId());
            AuthResponseDTO authResponseDTO = new AuthResponseDTO();
            authResponseDTO.setAccessToken(token);
            authResponseDTO.setTokenType("Bearer");
            authResponseDTO.setExpiresIn(3600);
            authResponseDTO.setRefreshToken(token);
            authResponseDTO.setProfile(profile);
            return ResponseEntity.status(HttpStatus.OK).body(authResponseDTO);
        }

        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}