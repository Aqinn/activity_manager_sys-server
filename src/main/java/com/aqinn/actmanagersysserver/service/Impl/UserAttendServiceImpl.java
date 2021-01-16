package com.aqinn.actmanagersysserver.service.Impl;

import com.aqinn.actmanagersysserver.CommonUtil;
import com.aqinn.actmanagersysserver.dao.*;
import com.aqinn.actmanagersysserver.entity.*;
import com.aqinn.actmanagersysserver.service.UserAttendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 1:13 下午
 */
@Service
public class UserAttendServiceImpl implements UserAttendService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AttendDao attendDao;

    @Autowired
    private ActDao actDao;

    @Autowired
    private UserActDao userActDao;

    @Autowired
    private UserAttendDao userAttendDao;

    @Override
    public int attend(UserAttend userAttend) {
        User user = userDao.queryUserById(userAttend.getuId());
        if (user == null)
            return -1;
        Attend attend = attendDao.queryAttendById(userAttend.getAttendId());
        if (attend == null)
            return -2;
        Long actId = attend.getActId();
        UserAct userAct = userActDao.queryUserAct(userAttend.getuId(), actId);
        if (userAct == null)
            return -3;
        Integer[] types = CommonUtil.dec2typeArr(attend.getType());
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(userAttend.getAttendType()))
                return userAttendDao.insertUserAttend(userAttend);
        }
        return -4;
    }

    @Override
    public UserAttend getUserAttend(Long userId, Long attendId) {
        return userAttendDao.queryUserAttend(userId, attendId);
    }

    @Override
    public int getUserAttendCount(Long attendId) {
        return userAttendDao.queryUserAttendCount(attendId);
    }

    @Override
    public List<UserAttend> getSelfUserAttendAfterTime(Long attendId, Long time) {
        return userAttendDao.querySelfUserAttendAfterTime(attendId, time);
    }

    @Override
    public List<UserAttend> getVideoUserAttendAfterTime(Long attendId, Long time) {
        return userAttendDao.queryVideoUserAttendAfterTime(attendId, time);
    }
}
