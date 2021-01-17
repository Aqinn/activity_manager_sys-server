package com.aqinn.actmanagersysserver.service.Impl;

import com.aqinn.actmanagersysserver.dao.ActDao;
import com.aqinn.actmanagersysserver.dao.AttendDao;
import com.aqinn.actmanagersysserver.entity.Act;
import com.aqinn.actmanagersysserver.entity.Attend;
import com.aqinn.actmanagersysserver.service.ActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 11:53 上午
 */
@Service
public class ActServiceImpl implements ActService {

    @Autowired
    private ActDao actDao;

    @Autowired
    private AttendDao attendDao;

    @Override
    public Long createAct(Act act) {
        if (actDao.insertAct(act) <= 0)
            return -1L;
        return act.getId();
    }

    @Override
    public Act getActById(Long id) {
        return actDao.queryActById(id);
    }

    @Override
    public Act getActByCode(Long code) {
        return actDao.queryActByCode(code);
    }

    @Override
    public List<Act> getUserCreateActs(Long uId) {
        return actDao.queryActByCreatorId(uId);
    }

    @Override
    public int modifyAct(Act act) {
        Act temp = actDao.queryActById(act.getId());
        if (temp == null)
            return -1;
        if (act.getName() != null)
            temp.setName(act.getName());
        if (act.getDesc() != null)
            temp.setDesc(act.getDesc());
        if (act.getLocation() != null)
            temp.setLocation(act.getLocation());
        if (act.getTime() != null)
            temp.setTime(act.getTime());
        return actDao.updateAct(temp);
    }

    @Override
    public int startAct(Long id) {
        Act act = actDao.queryActById(id);
        act.setIsOpen(2);
        return actDao.updateAct(act);
    }

    @Override
    public int stopAct(Long id) {
        Act act = actDao.queryActById(id);
        act.setIsOpen(3);
        List<Attend> attendList = attendDao.queryAttendByActId(id);
        for (Attend attend: attendList) {
            attendDao.stopAttend(attend.getId());
        }
        return actDao.updateAct(act);
    }

}
