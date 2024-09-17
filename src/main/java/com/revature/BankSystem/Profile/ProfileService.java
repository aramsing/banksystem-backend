package com.revature.BankSystem.Profile;

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
        if (isValidPassword(profile.getPassword()) == false) {
            throw new InvalidInputException("The password must be between 8-30 characters long and must have a lowercase letter, uppercase letter, number, and special character.");
        }

        return profileRepository.save(profile);
    }

    public Optional<Profile> getProfileByUsername(String username) {
        return profileRepository.findByUsername(username);
    }

    @Transactional
    public boolean updateProfile(Profile profile) {
        if (!isValidPassword(profile.getPassword())) {
            return false;
        }

        profileRepository.saveAndFlush(profile);
        return true;
    }

    public boolean deleteProfileById(int id) {
        profileRepository.deleteById(id);
        return true;
    }

    public boolean isValidPassword(String password) {
        String validPasswordRegex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}";

        if (!password.matches(validPasswordRegex)) {
            return false;
        }

        else {
            return true;
        }
    }
}