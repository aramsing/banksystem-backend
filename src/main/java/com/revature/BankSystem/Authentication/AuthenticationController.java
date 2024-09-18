package com.revature.BankSystem.Authentication;

import com.revature.BankSystem.DTO.RegisterDTO;
import com.revature.BankSystem.Profile.Profile;
import com.revature.BankSystem.Profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;
    private AuthenticationManager authenticationManager;
    private ProfileRepository profileRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, AuthenticationManager authenticationManager, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Profile> register(@RequestBody RegisterDTO registerDTO) {
        Profile profile = authenticationService.register(registerDTO);
        if (profile != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

//    @GetMapping("")
//    public ResponseEntity<Optional<Profile>> getProfileByUsernameAndPassword(@PathVariable String username) {
//
//    }
}
