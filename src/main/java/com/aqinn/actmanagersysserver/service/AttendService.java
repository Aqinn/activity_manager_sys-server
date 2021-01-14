package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.entity.Attend;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 12:04 下午
 */
public interface AttendService {

    Long createAttend(Attend attend);

    int removeAttendById(Long id);

    Attend getAttendById(Long id);

    int modifyAttend(Attend attend);

    List<Attend> getAttendByActId(Long actId);

    int startAttend(Long id);

    int stopAttend(Long id);

}
