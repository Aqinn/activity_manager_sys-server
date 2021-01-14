package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.entity.UserAttend;

/**
 * @Author Aqinn
 * @Date 2020/12/22 1:10 下午
 */
public interface UserAttendService {

    int attend(UserAttend userAttend);

    UserAttend getUserAttend(Long userId, Long attendId);

}
