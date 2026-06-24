package com.edigest.jorunalApp.service;

import com.edigest.jorunalApp.entity.User;
import com.edigest.jorunalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.apache.coyote.http11.Constants.a;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;



    @Disabled
    @ParameterizedTest
    @CsvSource({
            "ram",
            "ayush",
            "vipul"
    })
    public void testFindByUsername(String user){
        assertNotNull(userRepository.findByUserName(user),"failed for " + user);
    }


    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a, int b,int expected){
        assertEquals(expected,a + b);
    }

}
