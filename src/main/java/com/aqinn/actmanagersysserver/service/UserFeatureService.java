package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.entity.UserFeature;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2021/1/15 1:36 下午
 */
public interface UserFeatureService {

    int createFeatures(Long uId, String... features);

    int deleteAllFeatures(Long uId);

    List<UserFeature> queryUserFeaturesByActId(Long actId);

}
