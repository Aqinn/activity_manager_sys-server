package com.aqinn.actmanagersysserver.service.Impl;

import com.aqinn.actmanagersysserver.dao.UserDao;
import com.aqinn.actmanagersysserver.entity.User;
import com.aqinn.actmanagersysserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Aqinn
 * @Date 2020/12/22 12:01 下午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Long createUser(User user) {
        if (userDao.insertUser(user) <= 0L)
            return -1L;
        return user.getId();
    }

    @Override
    public int modifyUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public User getUserByAccount(String account) {
        return userDao.queryUserByAccount(account);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.queryUserById(id);
    }
}
