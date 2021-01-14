package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.entity.Act;
import com.aqinn.actmanagersysserver.entity.User;
import com.aqinn.actmanagersysserver.entity.UserAct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 11:35 上午
 */
public interface UserActDao {

    int insertUserAct(UserAct userAct);

    int removeUserAct(@Param("uId") Long uId, @Param("actId") Long actId);

    List<Act> queryUserActs(@Param("uId") Long uId);

    List<User> queryActUsers(@Param("actId") Long actId);

    UserAct queryUserAct(@Param("uId") Long uId, @Param("actId") Long actId);

}
