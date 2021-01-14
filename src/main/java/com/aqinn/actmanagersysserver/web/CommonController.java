package com.aqinn.actmanagersysserver.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aqinn.actmanagersysserver.ReturnData;
import com.aqinn.actmanagersysserver.entity.Act;
import com.aqinn.actmanagersysserver.entity.Attend;
import com.aqinn.actmanagersysserver.entity.User;
import com.aqinn.actmanagersysserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Aqinn
 * @Date 2020/12/25 3:29 下午
 */
@Controller
public class CommonController {

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

    @RequestMapping(value = "setup/{userId}/userdesc", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSetupUserDesc(@PathVariable("userId") Long userId) {
        ReturnData rd = new ReturnData();
        User user = userService.getUserById(userId);
        if (user == null)
            return rd.falseSuccess("用户不存在").buildReturnMap();
        JSONObject data = new JSONObject();
        data.put("id", user.getId());
        data.put("account", user.getAccount());
        data.put("name", user.getName());
        data.put("sex", user.getSex());
        data.put("contact", user.getContact());
        data.put("desc", user.getIntro());
        return rd.trueSuccess().setData(data).buildReturnMap();
    }

    @RequestMapping(value = "setup/{userId}/aii", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSetupActIntroItem(@PathVariable("userId") Long userId) {
        ReturnData rd = new ReturnData();
        User user = userService.getUserById(userId);
        if (user == null)
            return rd.falseSuccess("用户不存在").buildReturnMap();
        List<Act> partActList = userActService.getUserActs(userId);
        List<Act> createActList = actService.getUserCreateActs(userId);
        List<Act> totalActList = new ArrayList<>();
        totalActList.addAll(createActList);
        totalActList.addAll(partActList);
        JSONArray data = new JSONArray();
        for (Act act : totalActList) {
            JSONObject jo = new JSONObject();
            jo.put("ownerId", userId);
            jo.put("actId", act.getId());
            User temp = userService.getUserById(act.getuId());
            if (temp == null) {
                return rd.falseSuccess("查询活动创建者过程中出错，创建者不存在，请通知后台修复 bug").buildReturnMap();
            }
            jo.put("creator", temp.getAccount());
            jo.put("name", act.getName());
            jo.put("time", act.getTime());
            jo.put("location", act.getLocation());
            jo.put("intro", act.getDesc());
            jo.put("status", act.getIsOpen());
            jo.put("code", act.getCode());
            jo.put("pwd", act.getPwd());
            data.add(jo);
        }
        return rd.trueSuccess().setData(data).buildReturnMap();
    }

    @RequestMapping(value = "setup/{userId}/caii", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSetupCreateAttendIntroItem(@PathVariable("userId") Long userId) {
        ReturnData rd = new ReturnData();
        User user = userService.getUserById(userId);
        if (user == null)
            return rd.falseSuccess("用户不存在").buildReturnMap();
        List<Act> actList = actService.getUserCreateActs(userId);
        JSONArray data = new JSONArray();
        for (Act act : actList) {
            List<Attend> attendList = attendService.getAttendByActId(act.getId());
            for (Attend attend : attendList) {
                JSONObject jo = new JSONObject();
                jo.put("ownerId", userId);
                jo.put("attendId", attend.getId());
                jo.put("actId", act.getId());
                jo.put("name", act.getName());
                jo.put("time", attend.getTime());
                jo.put("type", attend.getType());
                jo.put("status", attend.getIsOpen());
                jo.put("shouldAttendCount", -1);
                jo.put("haveAttendCount", -1);
                jo.put("notAttendCount", -1);
                data.add(jo);
            }
        }
        return rd.trueSuccess().setData(data).buildReturnMap();
    }

    @RequestMapping(value = "setup/{userId}/paii", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSetupPartAttendIntroItem(@PathVariable("userId") Long userId) {
        ReturnData rd = new ReturnData();
        User user = userService.getUserById(userId);
        if (user == null)
            return rd.falseSuccess("用户不存在").buildReturnMap();
        List<Act> actList = userActService.getUserActs(userId);
        JSONArray data = new JSONArray();
        for (Act act : actList) {
            List<Attend> attendList = attendService.getAttendByActId(act.getId());
            for (Attend attend : attendList) {
                JSONObject jo = new JSONObject();
                jo.put("ownerId", userId);
                jo.put("attendId", attend.getId());
                jo.put("actId", act.getId());
                jo.put("name", act.getName());
                jo.put("time", attend.getTime());
                jo.put("type", attend.getType());
                boolean flag = userAttendService.getUserAttend(userId, attend.getId()) != null;
                jo.put("uStatus", flag ? 1 : 2);
                jo.put("status", attend.getIsOpen());
                data.add(jo);
            }
        }
        return rd.trueSuccess().setData(data).buildReturnMap();
    }

}
