package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.BaseTest;
import com.aqinn.actmanagersysserver.entity.Attend;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author Aqinn
 * @Date 2020/12/22 10:19 下午
 */
public class AttendDaoTest extends BaseTest {

    @Autowired
    private AttendDao attendDao;

    @Test
    public void insertAttend() {
        Attend attend = new Attend(15L, 1L, "12:00", 1, 0);
        attendDao.insertAttend(attend);
        System.out.println(attend.getId());
    }

    @Test
    public void removeAttend() {
        attendDao.removeAttend(2L);
    }

    @Test
    public void updateAttend() {
        Attend attend = attendDao.queryAttendById(1L);
        attend.setTime("00:00-23:59");
        attendDao.updateAttend(attend);
    }

    @Test
    public void queryAttendById() {
        Attend attend = attendDao.queryAttendById(1L);
        System.out.println(attend);
    }

    @Test
    public void queryAttendByActId() {
        List<Attend> attendList = attendDao.queryAttendByActId(1L);
        System.out.println(attendList);
    }
}