package com.aqinn.actmanagersysserver.web;

import com.alibaba.fastjson.JSONObject;
import com.aqinn.actmanagersysserver.ReturnData;
import com.aqinn.actmanagersysserver.entity.Act;
import com.aqinn.actmanagersysserver.entity.User;
import com.aqinn.actmanagersysserver.entity.UserAct;
import com.aqinn.actmanagersysserver.service.*;
import com.aqinn.actmanagersysserver.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author Aqinn
 * @Date 2020/12/22 2:48 下午
 */
@Controller
public class UserActController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActService actService;

    @Autowired
    private AttendService attendService;

    @Autowired
    private UserActService userActService;

    @Autowired
    private UserAttendService userAttendService;

    @Autowired
    private UserFeatureService userFeatureService;

    @RequestMapping(value = "/useract/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> joinAct(@PathVariable("userId") Long userId, HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("code") || !dataMap.containsKey("pwd")) {
            return rd.falseSuccess("提供的参数不完整").buildReturnMap();
        }
        Long code = Long.valueOf((String) dataMap.get("code"));
        Long pwd = Long.valueOf((String) dataMap.get("pwd"));
        final Act act = actService.getActByCode(code);
        if (act == null)
            return rd.falseSuccess("活动不存在").buildReturnMap();
        if (!act.getPwd().equals(pwd))
            return rd.falseSuccess("活动密码不正确").buildReturnMap();
        if (act.getuId().equals(userId))
            return rd.falseSuccess("不可以加入自己的活动").buildReturnMap();
        int res = userActService.joinAct(new UserAct(userId, act.getId()));
        if (res >= 1) {
            JSONObject data = new JSONObject();
            data.put("ownerId", userId);
            data.put("actId", act.getId());
            data.put("code", act.getCode());
            data.put("pwd", act.getPwd());
            User temp = userService.getUserById(act.getuId());
            if (temp == null) {
                return rd.falseSuccess("加入活动过程中出错，活动创建者不存在，请通知后台修复 bug").buildReturnMap();
            }
            data.put("creator", temp.getAccount());
            data.put("name", act.getName());
            data.put("time", act.getTime());
            data.put("location", act.getLocation());
            data.put("intro", act.getDesc());
            rd.trueSuccess().setData(data);
            if (UserFeatureController.actFeatures.containsKey(act.getId())) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserFeatureController.forceRefreshActFeatures(act.getId(), userFeatureService);
                    }
                }).start();
            }
        } else {
            if (res == 0)
                rd.falseSuccess("请勿重复加入该活动");
            else if (res == -1)
                rd.falseSuccess("用户不存在");
            else if (res == -2)
                rd.falseSuccess("活动不存在");
            else if (res == -3)
                rd.falseSuccess("活动签到进行中，禁止加入");
            else
                rd.falseSuccess("加入活动失败，未知原因");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/useract/{userId}/{actId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> quitAct(@PathVariable("userId") Long userId, @PathVariable("actId") Long actId) {
        ReturnData rd = new ReturnData();
        int res = userActService.quitAct(userId, actId);
        if (res >= 1) {
            rd.trueSuccess();
        } else {
            if (res == -1)
                rd.falseSuccess("用户不存在");
            else if (res == -2)
                rd.falseSuccess("活动不存在");
            else
                rd.falseSuccess("退出活动失败，可能是因为没有加入该活动");
        }
        return rd.buildReturnMap();
    }

}
