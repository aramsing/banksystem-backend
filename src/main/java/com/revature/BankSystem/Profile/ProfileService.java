package com.revature.BankSystem.Profile;

import com.revature.BankSystem.Exceptions.DataNotFoundException;
import com.revature.BankSystem.Exceptions.InvalidInputException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Profile getProfileById(int profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> new DataNotFoundException("No Profile found with profile Id Number" + profileId));
    }

    public Profile getProfileByUsername(String username) {
        return profileRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("No Profile found with username " + username));
    }

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
        try {
            profileRepository.deleteById(id);
            return true;
        }

        catch (Exception e) {
            return false;
        }
    }
}