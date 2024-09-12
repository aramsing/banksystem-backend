package com.revature.BankSystem.Profile;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/profiles")
@CrossOrigin
public class ProfileController {
    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<Profile> postCreateProfile(@RequestBody Profile profile) {
        Profile newProfile = profileService.createProfile(profile);

        if (newProfile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newProfile);
    }

    @GetMapping("?username={username}&password={password}")
    public ResponseEntity<Optional<Profile>> getProfileByUsernameAndPassword(@PathVariable String username, @PathVariable String password) {
        Optional<Profile> profile = profileService.getProfileByUsernameAndPassword(username, password);
        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @PutMapping
    public ResponseEntity<Boolean> putUpdateProfile(@Valid @RequestBody Profile profile) {
        boolean isUpdated = profileService.updateProfile(profile);

        if (isUpdated == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProfileById(@PathVariable int id) {
        boolean isDeleted = profileService.deleteProfileById(id);

        if (isDeleted == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}