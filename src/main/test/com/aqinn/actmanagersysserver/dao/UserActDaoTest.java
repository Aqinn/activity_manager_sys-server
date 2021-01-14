package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.BaseTest;
import com.aqinn.actmanagersysserver.entity.Act;
import com.aqinn.actmanagersysserver.entity.User;
import com.aqinn.actmanagersysserver.entity.UserAct;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author Aqinn
 * @Date 2020/12/22 10:23 下午
 */
public class UserActDaoTest extends BaseTest {

    @Autowired
    private UserActDao userActDao;

    @Test
    public void insertUserAct() {
        UserAct userAct = new UserAct(17L, 1L);
        userActDao.insertUserAct(userAct);
    }

    @Test
    public void removeUserAct() {
        userActDao.removeUserAct(15L, 1L);
    }

    @Test
    public void queryUserActs() {
        List<Act> actList = userActDao.queryUserActs(1L);
        System.out.println(actList);
    }

    @Test
    public void queryActUsers() {
        List<User> userList = userActDao.queryActUsers(1L);
        System.out.println(userList);
    }

    @Test
    public void queryUserAct() {
        UserAct userAct = userActDao.queryUserAct(15L, 2L);
        System.out.println(userAct);
    }

}