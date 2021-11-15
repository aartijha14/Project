package com.ttn.project2.config;

import com.ttn.project2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG ="user with %s not found";

    @Autowired
    private final UserRepository repo;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      // User user= repo.getUserByUserName(username);


        //CustomUserDetails customUserDetails= new CustomUserDetails(user);
        return repo.findByEmail(email).orElseThrow(
                ()->new UsernameNotFoundException
                        (String.format(USER_NOT_FOUND_MSG,email)));
    }

    public int enableUser(String email) {
        return repo.enableUser(email);
    }
}