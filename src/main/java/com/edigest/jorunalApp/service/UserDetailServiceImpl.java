package com.edigest.jorunalApp.service;

import com.edigest.jorunalApp.entity.User;
import com.edigest.jorunalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Fetch the real user from MongoDB
        User user = userRepository.findByUserName(username);

        // 2. If the user exists, hand the credentials over to Spring Security
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword()) // Pass the pre-saved BCrypt hash from DB
                    .roles("USER")
                    .build();
        }

        // 3. Throw exception if user is missing
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
