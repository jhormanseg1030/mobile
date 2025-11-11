package com.app.backend.security;

import com.app.backend.models.User;
import com.app.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.finbyUsername(username);
        .orElseThrow(() -> new  UsernameNotFoundException("Usario no encontrado" + username));

        return new org.springframework.sercurity.core.userdetails.User(user.getUsername(), user.getPassword(), user.getActive(), accountNonExpired: true, credentialsNonExpired: true
        , accountNonLocked: true, getAuthonrities(user));

    }

    private Collection<? extends GrantedAuthority>getAuthonrities(User user){
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }
}
