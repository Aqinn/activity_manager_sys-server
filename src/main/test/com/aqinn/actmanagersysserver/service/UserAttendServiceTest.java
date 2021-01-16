package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.BaseTest;
import com.aqinn.actmanagersysserver.entity.UserAttend;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Author Aqinn
 * @Date 2020/12/22 11:02 下午
 */
public class UserAttendServiceTest extends BaseTest {
    @Autowired
    private UserAttendService userAttendService;

    @Test
    public void attend() {
        System.out.println(userAttendService.attend(new UserAttend(15L, 4L, 1235L, 1)));
    }

}