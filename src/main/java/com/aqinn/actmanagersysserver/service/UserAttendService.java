package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.entity.UserAttend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 1:10 下午
 */
public interface UserAttendService {

    int attend(UserAttend userAttend);

    UserAttend getUserAttend(Long userId, Long attendId);

    int getUserAttendCount(Long attendId);

    List<UserAttend> getSelfUserAttendAfterTime(Long attendId, Long time);

    List<UserAttend> getVideoUserAttendAfterTime(Long attendId, Long time);

}
