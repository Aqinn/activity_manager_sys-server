package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.entity.User;

/**
 * @Author Aqinn
 * @Date 2020/12/22 11:58 上午
 */
public interface UserService {

    Long createUser(User user);

    int modifyUser(User user);

    User getUserByAccount(String account);

    User getUserById(Long id);

}
