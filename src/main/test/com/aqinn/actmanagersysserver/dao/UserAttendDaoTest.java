package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.BaseTest;
import com.aqinn.actmanagersysserver.entity.UserAttend;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Author Aqinn
 * @Date 2020/12/22 10:33 下午
 */
public class UserAttendDaoTest extends BaseTest {

    @Autowired
    private UserAttendDao userAttendDao;

    @Test
    public void insertUserAttend() {
        UserAttend userAttend = new UserAttend(15L, 1L, 1234L, 1);
        userAttendDao.insertUserAttend(userAttend);
    }
}