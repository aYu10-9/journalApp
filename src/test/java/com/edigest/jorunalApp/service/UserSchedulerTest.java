package com.edigest.jorunalApp.service;

import com.edigest.jorunalApp.scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {

    @Autowired
    private UserScheduler userScheduler;


    @Test
    public void  fetchUsersAndSendSaMail()
    {
        userScheduler.fetchUsersAndSendSaMail();
    }
}
