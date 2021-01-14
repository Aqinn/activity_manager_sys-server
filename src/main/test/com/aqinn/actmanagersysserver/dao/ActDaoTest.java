package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.BaseTest;
import com.aqinn.actmanagersysserver.entity.Act;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Author Aqinn
 * @Date 2020/12/22 10:07 下午
 */
public class ActDaoTest extends BaseTest {

    @Autowired
    private ActDao actDao;

    @Test
    public void insertAct() {
        Act act = new Act(15L, 123456L, 123456L, "海六篮球争霸赛", "冲冲冲", "海六", "00:59", 0);
        actDao.insertAct(act);
        System.out.println(act.getId());
    }

    @Test
    public void removeAct() {
        actDao.removeAct(2L);
    }

    @Test
    public void updateAct() {
        Act act = actDao.queryActById(1L);
        act.setTime("23:59");
        actDao.updateAct(act);
    }

    @Test
    public void queryActById() {
        Act act = actDao.queryActById(1L);
        System.out.println(act);
    }
}