package com.revature.BankSystem.Profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** REPOSITORY CLASS DOCUMENTATION
 * @author Arjun Ramsinghani
 * The Repository will use Jpa Repository to use its methods for database connections therefore no code will be written here unless we need something that is not there.
 */

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    /**
     * Finds a profile by the given username.
     * @param username - the username
     * @return an optional containing the profile if found, or an empty optional if no such profile exists
     */
    Optional<Profile> findByUsername(String username);
}