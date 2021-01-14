package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.BaseTest;
import com.aqinn.actmanagersysserver.entity.Attend;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Author Aqinn
 * @Date 2020/12/22 10:49 下午
 */
public class AttendServiceTest extends BaseTest {

    @Autowired
    private AttendService attendService;

    @Test
    public void createAttend() {
        Attend attend = new Attend(15L, 1L, "15:00", 1, 0);
        System.out.println(attendService.createAttend(attend));
    }

    @Test
    public void removeAttendById() {
    }

    @Test
    public void getAttendById() {
    }

    @Test
    public void modifyAttend() {
    }

    @Test
    public void getAttendByActId() {
    }
}