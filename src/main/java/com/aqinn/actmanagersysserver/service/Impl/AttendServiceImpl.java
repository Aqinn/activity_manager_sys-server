package com.aqinn.actmanagersysserver.service.Impl;

import com.aqinn.actmanagersysserver.dao.ActDao;
import com.aqinn.actmanagersysserver.dao.AttendDao;
import com.aqinn.actmanagersysserver.dao.UserDao;
import com.aqinn.actmanagersysserver.entity.Attend;
import com.aqinn.actmanagersysserver.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 12:40 下午
 */
@Service
public class AttendServiceImpl implements AttendService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ActDao actDao;

    @Autowired
    private AttendDao attendDao;

    @Override
    public Long createAttend(Attend attend) {
        if (userDao.queryUserById(attend.getuId()) == null)
            return -1L;
        if (actDao.queryActById(attend.getActId()) == null)
            return -2L;
        attend.setIsOpen(1);
        if (attendDao.insertAttend(attend) <= 0)
            return -3L;
        return attend.getId();
    }

    @Override
    public int removeAttendById(Long id) {
        return attendDao.removeAttend(id);
    }

    @Override
    public int modifyAttend(Attend attend) {
        if (attendDao.queryAttendById(attend.getId()) == null)
            return -1;
        return attendDao.updateAttend(attend);
    }

    @Override
    public Attend getAttendById(Long id) {
        return attendDao.queryAttendById(id);
    }

    @Override
    public List<Attend> getAttendByActId(Long actId) {
        return attendDao.queryAttendByActId(actId);
    }

    @Override
    public int startAttend(Long id) {
        return attendDao.startAttend(id);
    }

    @Override
    public int stopAttend(Long id) {
        return attendDao.stopAttend(id);
    }
}
