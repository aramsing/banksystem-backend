package com.revature.BankSystem.Profile;

import com.revature.BankSystem.Exceptions.DataNotFoundException;
import com.revature.BankSystem.Exceptions.InvalidInputException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(Profile profile) {
        if (profile.getPassword().length() <= 8) {
            throw new InvalidInputException("The password must be 8 characters long .");
        }

        return profileRepository.save(profile);
    }

//    public Optional<Profile> getProfileByUsername(String username) {
//        return profileRepository.findByUsername(username);
//    }

    public Profile getProfileById(int profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> new DataNotFoundException("No Profile found with profile Id Number" + profileId));
    }

//    public int lookupProfileIdByEmail(String email) {
//        return profileRepository.findByEmail(email).orElseThrow().getId();
//    }

    public int lookupProfileIdByUsername(String username) {
        return profileRepository.findByUsername(username).orElseThrow().getId();
    }

    @Transactional
    public boolean updateProfile(Profile profile) {
        if (profile.getPassword().length() <= 8) {
            throw new InvalidInputException("The password must be 8 characters long .");
        }

        profileRepository.saveAndFlush(profile);
        return true;
    }

    public boolean deleteProfileById(int id) {
        profileRepository.deleteById(id);
        return true;
    }

//    public boolean isValidPassword(String password) {
//        String validPasswordRegex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}";
//
//        if (!password.matches(validPasswordRegex)) {
//            return false;
//        }
//
//        else {
//            return true;
//        }
//    }
}