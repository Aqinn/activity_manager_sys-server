package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Aqinn
 * @Date 2020/12/22 10:54 上午
 */
public interface UserDao {

    int insertUser(User user);

    int updateUser(@Param("user") User user);

    User queryUserById(@Param("id") Long id);

    User queryUserByAccount(@Param("account") String account);

}
