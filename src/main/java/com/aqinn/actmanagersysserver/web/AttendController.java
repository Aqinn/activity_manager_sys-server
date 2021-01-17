package com.aqinn.actmanagersysserver.web;

import com.alibaba.fastjson.JSONObject;
import com.aqinn.actmanagersysserver.ReturnData;
import com.aqinn.actmanagersysserver.entity.Attend;
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
 * @Date 2020/12/22 2:56 下午
 */
@Controller
public class AttendController {

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


    // TODO 签到的类型需要判断
    @RequestMapping(value = "/attend/{userId}/{actId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createAttend(@PathVariable("userId") Long userId, @PathVariable("actId") Long actId, HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("time") || !dataMap.containsKey("type")) {
            return rd.falseSuccess("提供的参数不完整").buildReturnMap();
        }
        int type = Integer.parseInt((String) dataMap.get("type"));
        if (type > 3 || type < 1)
            return rd.falseSuccess("签到类型不存在").buildReturnMap();
        Attend attend = new Attend(userId, actId, (String) dataMap.get("time"), type, 1);
        Long id = attendService.createAttend(attend);
        if (id >= 1L) {
            JSONObject jo = new JSONObject();
            jo.put("ownerId", userId);
            jo.put("attendId", id);
            jo.put("actName", actService.getActById(actId).getName());
            jo.put("shouldAttendCount", userActService.getActUsers(actId).size());
            rd.trueSuccess().setData(jo.toString());
        } else {
            if (id == -1)
                rd.falseSuccess("用户不存在");
            else if (id == -2)
                rd.falseSuccess("活动不存在");
            else if (id == -3)
                rd.falseSuccess("创建签到失败，未知原因");
            else
                rd.falseSuccess("创建签到失败，未知原因");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/attend/{attendId}/time", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> editAttendTime(@PathVariable("attendId") Long attendId, HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("time")) {
            return rd.falseSuccess("提供的参数不完整").buildReturnMap();
        }
        Attend attend = attendService.getAttendById(attendId);
        if (attend == null) {
            return rd.falseSuccess("签到不存在").buildReturnMap();
        }
        attend.setTime((String) dataMap.get("time"));
        int res = attendService.modifyAttend(attend);
        if (res >= 1) {
            rd.trueSuccess();
        } else {
            if (res == -1)
                rd.falseSuccess("签到不存在");
            else
                rd.falseSuccess("修改签到时间失败，未知原因");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/attend/{attendId}/type", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> editAttendType(@PathVariable("attendId") Long attendId, HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("type")) {
            rd.falseSuccess("提供的参数不完整").buildReturnMap();
        }
        int type = Integer.parseInt((String) dataMap.get("type"));
        if (type > 3 || type < 1)
            return rd.falseSuccess("签到类型不存在").buildReturnMap();
        Attend attend = attendService.getAttendById(attendId);
        if (attend == null) {
            return rd.falseSuccess("签到不存在").buildReturnMap();
        }
        attend.setType(type);
        int res = attendService.modifyAttend(attend);
        if (res >= 1) {
            rd.trueSuccess();
        } else {
            if (res == -1)
                rd.falseSuccess("签到不存在");
            else
                rd.falseSuccess("修改签到时间失败，未知原因");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/attend/{attendId}/start", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> startAttend(@PathVariable("attendId") Long attendId) {
        ReturnData rd = new ReturnData();
        Attend attend = attendService.getAttendById(attendId);
        if (attend == null) {
            return rd.falseSuccess("签到不存在").buildReturnMap();
        }
        int res = attendService.startAttend(attendId);
        if (res >= 1) {
            rd.trueSuccess();
        } else {
            if (res == 0)
                rd.falseSuccess("签到已经是开启状态");
            else if (res == -1){
                rd.falseSuccess("活动尚未开始");
            }
            else
                rd.falseSuccess("开启签到失败，未知原因");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/attend/{attendId}/stop", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> stopAttend(@PathVariable("attendId") Long attendId) {
        ReturnData rd = new ReturnData();
        Attend attend = attendService.getAttendById(attendId);
        if (attend == null) {
            return rd.falseSuccess("签到不存在").buildReturnMap();
        }
        int res = attendService.stopAttend(attendId);
        if (res >= 1) {
            rd.trueSuccess();
        } else {
            if (res == 0)
                rd.falseSuccess("签到已经是关闭状态");
            else
                rd.falseSuccess("关闭签到失败，未知原因");
        }
        return rd.buildReturnMap();
    }


}
