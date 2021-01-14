package com.aqinn.actmanagersysserver.dao;

import com.aqinn.actmanagersysserver.entity.Attend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 11:19 上午
 */
public interface AttendDao {

    int insertAttend(Attend attend);

    int removeAttend(@Param("id") Long id);

    int updateAttend(Attend attend);

    Attend queryAttendById(@Param("id") Long id);

    List<Attend> queryAttendByActId(@Param("actId") Long actId);

    int startAttend(@Param("id") Long id);

    int stopAttend(@Param("id") Long id);

}
