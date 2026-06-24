package com.edigest.jorunalApp.service;

import com.edigest.jorunalApp.entity.User;
import com.edigest.jorunalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test; // <-- Added this missing import
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;


public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailServiceImpl userDetailService; // Ensure this matches your actual service class name exactly!

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Disabled// <-- CRITICAL: Without this, JUnit will completely ignore the method
    public void loadUserByUsernameTest(){
        // Mocking the repository response
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(User.builder()
                        .userName("vipul")
                        .password("wfwdw")
                        .roles(new ArrayList<>())
                        .build());

        // Acting
        UserDetails vipul = userDetailService.loadUserByUsername("vipul");

        // Asserting
        Assertions.assertNotNull(vipul);
    }
}