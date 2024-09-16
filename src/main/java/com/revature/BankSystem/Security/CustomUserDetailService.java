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

    public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(profile.getUsername(), profile.getPassword(), mapRolesToAuthorities(roles));
    }

    @Autowired
    public CustomUserDetailService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        roles.add("CUSTOMER");
        roles.add("EMPLOYEE");
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
