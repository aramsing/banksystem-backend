package com.revature.BankSystem.Profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    /**
     * Finds a profile by the given username.
     *
     * @param username The username.
     * @return An optional containing the profile if found, or an empty optional if no such profile exists.
     */
    Optional<Profile> findByUsername(String username);
}