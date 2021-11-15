package com.ttn.project2.config;

import com.ttn.project2.Model.Role;
import com.ttn.project2.Model.User;
import com.ttn.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Transactional
public class WebUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public WebUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmailIgnoreCase(username);
            if (user == null) {
                return null;
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Set<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : user.getRole()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getAuthority());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}