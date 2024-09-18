package com.revature.BankSystem.Authentication;

import com.revature.BankSystem.DTO.AuthResponseDTO;
import com.revature.BankSystem.DTO.LoginDTO;
import com.revature.BankSystem.DTO.RegisterDTO;
import com.revature.BankSystem.Profile.Profile;
import com.revature.BankSystem.Profile.ProfileService;
import com.revature.BankSystem.Security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
        Profile registeredProfile = new Profile(registerDTO.getUsername(), passwordEncoder.encode(registerDTO.getPassword()), registerDTO.getEmail(), registerDTO.getFirstName(), registerDTO.getLastName(), registerDTO.getAddress(), registerDTO.getUserRole());
        Profile newProfile = profileService.createProfile(registeredProfile);
        return ResponseEntity.status(HttpStatus.OK).body(newProfile);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        int id = profileService.lookupProfileIdByUsername(loginDTO.getUsername());
        String token = jwtGenerator.generateToken(authentication, id);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO(token));
    }
}