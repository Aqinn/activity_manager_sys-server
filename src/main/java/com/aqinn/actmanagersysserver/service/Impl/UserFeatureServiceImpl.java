package com.aqinn.actmanagersysserver.service.Impl;

import com.aqinn.actmanagersysserver.dao.UserFeatureDao;
import com.aqinn.actmanagersysserver.entity.UserFeature;
import com.aqinn.actmanagersysserver.service.UserFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2021/1/15 1:38 下午
 */
@Service
public class UserFeatureServiceImpl implements UserFeatureService {

    @Autowired
    private UserFeatureDao userFeatureDao;

    @Override
    public int createFeatures(Long uId, String... features) {
        boolean flag = true;
        for (int i = 0; i < features.length; i++) {
            UserFeature userFeature = new UserFeature(uId, features[i]);
            if (userFeatureDao.insertFeature(userFeature) < 0)
                flag = false;
        }
        return flag ? 0 : -1;
    }

    @Override
    public int deleteAllFeatures(Long uId) {
        return userFeatureDao.removeAll(uId);
    }

    @Override
    public List<UserFeature> queryUserFeaturesByActId(Long actId) {
        return userFeatureDao.queryByActId(actId);
    }

}
