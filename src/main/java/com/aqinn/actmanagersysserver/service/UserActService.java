package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.entity.Act;
import com.aqinn.actmanagersysserver.entity.User;
import com.aqinn.actmanagersysserver.entity.UserAct;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 12:43 下午
 */
public interface UserActService {

    int joinAct(UserAct userAct);

    int quitAct(Long uId, Long actId);

    List<Act> getUserActs(Long uId);

    List<User> getActUsers(Long actId);

    UserAct getUserAct(Long uId, Long actId);

}
