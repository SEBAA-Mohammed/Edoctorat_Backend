package com.estf.edoctorat.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.estf.edoctorat.models.AuthGroupModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by email
        UserModel user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Create authorities from user groups
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getUserGroup() != null && user.getUserGroup().getGroups() != null) {
            for (AuthGroupModel group : user.getUserGroup().getGroups()) {
                authorities.add(new SimpleGrantedAuthority(group.getNom()));
            }
        }

        return new CustomUserDetails(user, authorities);
    }
}
