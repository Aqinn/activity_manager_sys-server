package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.entity.Act;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 11:03 上午
 */
public interface ActDao {

    int insertAct(Act act);

    int removeAct(@Param("id") Long id);

    int updateAct(@Param("act") Act act);

    Act queryActById(@Param("id") Long id);

    Act queryActByCode(@Param("code") Long code);

    List<Act> queryActByCreatorId(@Param("uId") Long uId);

}
