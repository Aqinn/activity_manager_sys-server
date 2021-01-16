package com.aqinn.actmanagersysserver.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aqinn.actmanagersysserver.ReturnData;
import com.aqinn.actmanagersysserver.entity.Act;
import com.aqinn.actmanagersysserver.entity.Attend;
import com.aqinn.actmanagersysserver.entity.User;
import com.aqinn.actmanagersysserver.entity.UserAttend;
import com.aqinn.actmanagersysserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Aqinn
 * @Date 2020/12/22 3:12 下午
 */
@Controller
public class UserAttendController {

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

    @RequestMapping(value = "/userattend/{userId}/{attendId}/{attendType}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userAttend(@PathVariable("userId") Long userId, @PathVariable("attendId") Long attendId
            , @PathVariable("attendType") Integer attendType) {
        ReturnData rd = new ReturnData();
        if (1 > attendType || attendType > 2) {
            return rd.falseSuccess("未知签到类型").buildReturnMap();
        }
        UserAttend userAttend = new UserAttend(userId, attendId, System.currentTimeMillis(), attendType);
        int res = userAttendService.attend(userAttend);
        if (res >= 1) {
            rd.trueSuccess();
        } else {
            if (res == -1)
                rd.falseSuccess("用户不存在");
            else if (res == -2)
                rd.falseSuccess("签到不存在");
            else if (res == -3)
                rd.falseSuccess("用户没有参与该活动，无法签到");
            else if (res == -4)
                rd.falseSuccess("本次签到中，该签到方式不存在");
            else if (res == 0)
                rd.falseSuccess("请勿重复签到");
            else
                rd.falseSuccess("签到失败，未知原因");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/userattend/{attendId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAttendCount(@PathVariable("attendId") Long attendId) {
        ReturnData rd = new ReturnData();
        Attend attend = attendService.getAttendById(attendId);
        if (attend == null)
            return rd.falseSuccess("签到不存在").buildReturnMap();
        int haveAttendCount = userAttendService.getUserAttendCount(attendId);
        List<User> userList = userActService.getActUsers(attend.getActId());
        if (userList == null)
            return rd.falseSuccess("签到对应的活动不存在，后台的锅").buildReturnMap();
        int shouldAttendCount = userList.size();
        JSONObject jo = new JSONObject();
        jo.put("haveAttendCount", haveAttendCount);
        jo.put("shouldAttendCount", shouldAttendCount);
        return rd.trueSuccess().setData(jo.toString()).buildReturnMap();
    }

    @RequestMapping(value = "/userattend/self/{attendId}/{timestamp}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSelfUserAttendAfterTime(@PathVariable("attendId") Long attendId, @PathVariable("timestamp") Long timestamp) {
        ReturnData rd = new ReturnData();
        Attend attend = attendService.getAttendById(attendId);
        if (attend == null)
            return rd.falseSuccess("签到不存在").buildReturnMap();
        // TODO 这里还应该判断更多。。。先不写了
        List<UserAttend> userAttendList = userAttendService.getSelfUserAttendAfterTime(attendId, timestamp);
        JSONArray data = new JSONArray();
        for (UserAttend ua:userAttendList) {
            JSONObject jo = new JSONObject();
            User user = userService.getUserById(ua.getuId());
            jo.put("msg", "账号:"+user.getAccount() + ", 昵称:" + user.getName()+"（自助签到）");
            jo.put("attendTime", ua.getAttendTime());
            data.add(jo);
        }
        return rd.trueSuccess().setData(data.toString()).buildReturnMap();
    }

    @RequestMapping(value = "/userattend/video/{attendId}/{timestamp}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getVideoUserAttendAfterTime(@PathVariable("attendId") Long attendId, @PathVariable("timestamp") Long timestamp) {
        ReturnData rd = new ReturnData();
        Attend attend = attendService.getAttendById(attendId);
        if (attend == null)
            return rd.falseSuccess("签到不存在").buildReturnMap();
        // TODO 这里还应该判断更多。。。先不写了
        List<UserAttend> userAttendList = userAttendService.getVideoUserAttendAfterTime(attendId, timestamp);
        JSONArray data = new JSONArray();
        for (UserAttend ua:userAttendList) {
            JSONObject jo = new JSONObject();
            User user = userService.getUserById(ua.getuId());
            jo.put("msg", "账号:"+user.getAccount() + ", 昵称:" + user.getName()+"（视频签到）");
            jo.put("attendTime", ua.getAttendTime());
            data.add(jo);
        }
        return rd.trueSuccess().setData(data.toString()).buildReturnMap();
    }

}
