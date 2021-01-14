package com.aqinn.actmanagersysserver.service.Impl;

import com.aqinn.actmanagersysserver.dao.UserActDao;
import com.aqinn.actmanagersysserver.entity.Act;
import com.aqinn.actmanagersysserver.entity.User;
import com.aqinn.actmanagersysserver.entity.UserAct;
import com.aqinn.actmanagersysserver.service.ActService;
import com.aqinn.actmanagersysserver.service.UserActService;
import com.aqinn.actmanagersysserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 1:09 下午
 */
@Service
public class UserActServiceImpl implements UserActService {

    @Autowired
    private UserActDao userActDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ActService actService;

    @Override
    public int joinAct(UserAct userAct) {
        User user = userService.getUserById(userAct.getuId());
        if (user == null)
            return -1;
        Act act = actService.getActById(userAct.getActId());
        if (act == null)
            return -2;
        return userActDao.insertUserAct(userAct);
    }

    @Override
    public int quitAct(Long uId, Long actId) {
        User user = userService.getUserById(uId);
        if (user == null)
            return -1;
        Act act = actService.getActById(actId);
        if (act == null)
            return -2;
        return userActDao.removeUserAct(uId, actId);
    }

    @Override
    public List<Act> getUserActs(Long uId) {
        return userActDao.queryUserActs(uId);
    }

    @Override
    public List<User> getActUsers(Long actId) {
        return userActDao.queryActUsers(actId);
    }

    @Override
    public UserAct getUserAct(Long uId, Long actId) {
        return userActDao.queryUserAct(uId, actId);
    }
}
