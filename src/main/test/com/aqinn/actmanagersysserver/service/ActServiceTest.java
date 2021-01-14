package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.BaseTest;
import com.aqinn.actmanagersysserver.entity.Act;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Author Aqinn
 * @Date 2020/12/22 10:41 下午
 */
public class ActServiceTest extends BaseTest {

    @Autowired
    private ActService actService;

    @Test
    public void createAct() {
        Act act = new Act(15L, 123456L, 123456L, "海七足球联赛", "冲冲冲", "海六", "00:59", 0);
        System.out.println(actService.createAct(act));
    }

    @Test
    public void getActById() {
    }

    @Test
    public void modifyAct() {
        Act act = actService.getActById(3L);
        act.setTime("12:34");
        System.out.println(actService.modifyAct(act));
    }

    @Test
    public void startAct() {
        System.out.println(actService.startAct(3L));
    }

    @Test
    public void stopAct() {
        System.out.println(actService.stopAct(3L));
    }
}