package com.aqinn.actmanagersysserver.service;

import com.aqinn.actmanagersysserver.entity.Act;

import java.util.List;

/**
 * @Author Aqinn
 * @Date 2020/12/22 11:42 上午
 */
public interface ActService {

    Long createAct(Act act);

    Act getActById(Long id);

    Act getActByCode(Long code);

    int modifyAct(Act act);

    int startAct(Long id);

    int stopAct(Long id);

    List<Act> getUserCreateActs(Long uId);

}
