package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.entity.UserFeature;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2021/1/15 1:25 下午
 */
public interface UserFeatureDao {

    int insertFeature(UserFeature userFeature);

    int removeAll(Long uId);

    List<UserFeature> queryByActId(Long actId);

}
