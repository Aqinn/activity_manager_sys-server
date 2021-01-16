package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.entity.UserAttend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 11:39 上午
 */
public interface UserAttendDao {

    int insertUserAttend(UserAttend userAttend);

    UserAttend queryUserAttend(@Param("userId") Long userId, @Param("attendId") Long attendId);

    int queryUserAttendCount(@Param("attendId") Long attendId);

    List<UserAttend> querySelfUserAttendAfterTime(@Param("attendId") Long attendId, @Param("time") Long time);

    List<UserAttend> queryVideoUserAttendAfterTime(@Param("attendId") Long attendId, @Param("time") Long time);

}