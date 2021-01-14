package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.BaseTest;
import com.aqinn.actmanagersysserver.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Aqinn
 * @Date 2020/12/22 9:02 下午
 */
public class UserDaoTest extends BaseTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void insertUser() {
        User user = new User("zbc", "123456", "Aqinn", "13192205220", 1, "我是一个好人。");
        userDao.insertUser(user);
        System.out.println("user.getId() => " + user.getId());
    }

    @Test
    public void updateUser() {
        User user = userDao.queryUserById(15L);
        user.setAccount("zjf");
        userDao.updateUser(user);
    }

    @Test
    public void queryUserById() {
        User user = userDao.queryUserById(15L);
        System.out.println(user);
    }

    @Test
    public void queryUserByAccount() {
        User user = userDao.queryUserByAccount("zjf");
        System.out.println(user);
    }
}