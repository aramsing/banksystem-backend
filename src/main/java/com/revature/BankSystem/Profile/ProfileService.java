package com.revature.BankSystem.Profile;

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

    // create user profile
    // log in
    // update user profile
    // delete user profile

    public Profile createProfile(Profile profile) {
        if (!isValidPassword(profile.getPassword())) {
            return null;
        }

        return profileRepository.save(profile);
    }

    public Optional<Profile> getProfileByUsernameAndPassword(String username, String password) {
        return profileRepository.findByUsernameAndPassword(username, password);
    }

    public boolean updateProfile(Profile profile) {
        if (!isValidPassword(profile.getPassword())) {
            return false;
        }

        profileRepository.saveAndFlush(profile);
        return true;
    }

    public boolean deleteProfile(int id) {
        profileRepository.deleteById(id);
        return true;
    }

    public boolean isValidPassword(String password) {
        String validPasswordRegex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10,30}";

        if (!password.matches(validPasswordRegex)) {
            return false;
        }

        return true;
    }
}