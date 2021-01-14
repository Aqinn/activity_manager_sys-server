package com.aqinn.actmanagersysserver.web;

import com.alibaba.fastjson.JSONObject;
import com.aqinn.actmanagersysserver.ReturnData;
import com.aqinn.actmanagersysserver.entity.Act;
import com.aqinn.actmanagersysserver.service.*;
import com.aqinn.actmanagersysserver.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @Author Aqinn
 * @Date 2020/12/22 1:14 下午
 */
@Controller
public class ActController {

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

    @RequestMapping(value = "/act", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createAct(HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("uId") || !dataMap.containsKey("name") ||
                !dataMap.containsKey("desc") || !dataMap.containsKey("location") ||
                !dataMap.containsKey("time")) {
            return rd.falseSuccess("提供的参数不完整").buildReturnMap();
        }
        Long uId = Long.valueOf((String) dataMap.get("uId"));
        String name = (String) dataMap.get("name");
        String desc = (String) dataMap.get("desc");
        String location = (String) dataMap.get("location");
        String time = (String) dataMap.get("time");
        Long code = -1L;
        do {
            code = new Date().getTime() % 1000000L;
        } while (code < 100000L);
        Long pwd = code;
        Act act = new Act(uId, code, pwd, name, desc, location, time, 1);
        Long id = actService.createAct(act);
        if (id != -1L) {
            JSONObject data = new JSONObject();
            data.put("id", id);
            data.put("code", code);
            data.put("pwd", pwd);
            rd.trueSuccess().setData(data);
        } else {
            rd.falseSuccess("创建活动失败, 可能是 1.活动code重复插入,请再创建一次试试 2.创建者不存在");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/act/{actId}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> editAct(@PathVariable("actId") Long actId, HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        Act act = actService.getActById(actId);
        if (act == null) {
            return rd.falseSuccess("活动不存在").buildReturnMap();
        }
        if (dataMap.containsKey("name")) {
            act.setName((String) dataMap.get("name"));
        }
        if (dataMap.containsKey("desc")) {
            act.setDesc((String) dataMap.get("desc"));
        }
        if (dataMap.containsKey("location")) {
            act.setLocation((String) dataMap.get("location"));
        }
        if (dataMap.containsKey("time")) {
            act.setTime((String) dataMap.get("time"));
        }
        int res = actService.modifyAct(act);
        if (res >= 1) {
            rd.trueSuccess();
        } else {
            rd.falseSuccess("修改活动失败，未知原因");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/act/{actId}/start", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> startAct(@PathVariable("actId") Long actId) {
        ReturnData rd = new ReturnData();
        int res = actService.startAct(actId);
        if (res >= 1) {
            rd.trueSuccess();
        } else {
            rd.falseSuccess("活动不存在");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/act/{actId}/stop", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> stopAct(@PathVariable("actId") Long actId) {
        ReturnData rd = new ReturnData();
        int res = actService.stopAct(actId);
        if (res >= 1) {
            rd.trueSuccess();
        } else {
            rd.falseSuccess("活动不存在");
        }
        return rd.buildReturnMap();
    }


}
