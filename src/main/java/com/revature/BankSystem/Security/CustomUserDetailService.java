package com.revature.BankSystem.Security;

import com.revature.BankSystem.Profile.Profile;
import com.revature.BankSystem.Profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService {
    private ProfileRepository profileRepository;
    private ArrayList<String> roles = new ArrayList<>();

    /**
     * This method is used to load a user by their username.
     * It returns a Spring Security UserDetails object which is used to authenticate the user.
     *
     * @param username The username to be loaded.
     * @return A UserDetails object containing the users information.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found")); // if the user is found, return the user
        return new User(profile.getUsername(), profile.getPassword(), mapRolesToAuthorities(roles));
    }

    @Autowired
    public CustomUserDetailService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        roles.add("CUSTOMER");
        roles.add("EMPLOYEE");
    }

    /**
     * Maps a list of roles to a collection of granted authorities.
     * @param roles A list of roles
     * @return A collection of granted authorities
     */
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()); // map roles to granted authorities using Stream API
    }
}