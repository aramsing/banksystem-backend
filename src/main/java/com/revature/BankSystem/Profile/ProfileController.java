package com.revature.BankSystem.Profile;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Updates the given profile with the given information.
     * @param profile The profile to update.
     * @return true if the profile was updated successfully, false otherwise.
     */
    @PutMapping
    public ResponseEntity<Boolean> putUpdateProfile(@Valid @RequestBody Profile profile) {
        boolean isUpdated = profileService.updateProfile(profile); // update the profile

        if (isUpdated == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false); // return false if the profile was not updated
        }

        return ResponseEntity.status(HttpStatus.OK).body(true); // return true if the profile was updated
    }

    /**
     * Deletes the profile with the given id.
     * @param id the id of the profile to delete
     * @return true if the profile was deleted successfully, false otherwise.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProfileById(@PathVariable int id) {
        boolean isDeleted = profileService.deleteProfileById(id); // delete the profile

        if (isDeleted == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false); // return false if the profile was not deleted
        }

        return ResponseEntity.status(HttpStatus.OK).body(true); // return true if the profile was deleted
    }
}