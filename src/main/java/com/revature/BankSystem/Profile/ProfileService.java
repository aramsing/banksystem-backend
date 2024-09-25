package com.revature.BankSystem.Profile;

import com.revature.BankSystem.Exceptions.DataNotFoundException;
import com.revature.BankSystem.Exceptions.InvalidInputException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** SERVICE CLASS DOCUMENTATION
 * @author Arjun Ramsinghani
 * The Service class is used to define the business logic coming to the backend by use of Spring Boot and will communicate with the database through Spring Data.
 * Instructions on how each method should interact are within each method call.
 */

@Service
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Creates a new profile with the given information and adds it to the database.
     * The password must be at least 8 characters long.
     * @param profile The profile information to be added to the database
     * @return The newly added profile
     * @throws InvalidInputException If the password is less than 8 characters long
     */
    public Profile createProfile(Profile profile) {
        if (profile.getPassword().length() <= 8) {
            throw new InvalidInputException("The password must be 8 characters long ."); // throw exception if password is less than 8 characters
        }

        return profileRepository.save(profile); // save the profile, then return it
    }

    /**
     * Gets a profile by the given id.
     * @param id the profile id to search for
     * @return the profile with the given id
     * @throws DataNotFoundException if no profile with the given id is found
     */
    public Profile getProfileById(int id) {
        return profileRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No Profile found with profile Id Number" + id));
    }

    /**
     * Gets a profile by the given username.
     * @param username the username to search for
     * @return the profile with the given username
     * @throws DataNotFoundException if no profile with the given username is found
     */
    public Profile getProfileByUsername(String username) {
        return profileRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("No Profile found with username " + username));
    }

    /**
     * Looks up the profile id for the given username.
     * @param username the username to look up
     * @return the profile id associated with the given username
     * @throws DataNotFoundException if no profile with the given username is found
     */
    public int lookupProfileIdByUsername(String username) {
        return profileRepository.findByUsername(username).orElseThrow().getId();
    }

    /**
     * Updates the given profile with the given information.
     * @param updatedProfile the profile to update
     * @return true if the profile was updated successfully, false otherwise
     * @throws DataNotFoundException if no profile with the given id is found
     * @throws InvalidInputException if the password is less than 8 characters long
     */
    @Transactional
    public boolean updateProfile(Profile updatedProfile) {
        boolean exists = profileRepository.existsById(updatedProfile.getId()); // check if profile exists

        if (!exists) {
            throw new DataNotFoundException("No Profile found with profile Id Number " + updatedProfile.getId()); // throw exception if profile does not exist
        }

        if (updatedProfile.getPassword().length() <= 8) {
            throw new InvalidInputException("Password must be at least 8 characters long."); // throw exception if password is less than 8 characters
        }

        profileRepository.save(updatedProfile); // save the updated profile, then return true
        return true;
    }

    /**
     * Deletes the profile with the given id.
     * @param id the id of the profile to delete
     * @return true if the profile was deleted successfully, false otherwise
     */
    public boolean deleteProfileById(int id) {
        try {
            profileRepository.deleteById(id); // if we are able to delete it, return true
            return true;
        }

        catch (Exception e) { // if we are unable to delete it, return false
            return false;
        }
    }
}