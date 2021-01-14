package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.BaseTest;
import com.aqinn.actmanagersysserver.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Author Aqinn
 * @Date 2020/12/22 10:38 下午
 */
public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        User user = new User("zzq", "123456", "Aqinn", "13192205220", 1, "我是一个好人。");
        System.out.println(userService.createUser(user));
    }

    @Test
    public void modifyUser() {
    }

    @Test
    public void getUserByAccount() {

    }

    @Test
    public void getUserById() {
    }
}