package com.revature.BankSystem.Profile;

import com.revature.BankSystem.Exceptions.DataNotFoundException;
import com.revature.BankSystem.Exceptions.InvalidInputException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
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

    public Profile getProfileById(int profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> new DataNotFoundException("No Profile found with profile Id Number" + profileId));
    }

    public Profile getProfileByUsernameAndPassword(String username, String password) throws AuthenticationException {
        return profileRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> new AuthenticationException("Incorrect credentials"));
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
        profileRepository.deleteById(id);
        return true;
    }
}