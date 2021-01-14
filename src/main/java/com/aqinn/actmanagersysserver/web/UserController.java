package com.aqinn.actmanagersysserver.web;

import com.alibaba.fastjson.JSONObject;
import com.aqinn.actmanagersysserver.ReturnData;
import com.aqinn.actmanagersysserver.entity.User;
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
 * @Date 2020/12/22 2:22 下午
 */
@Controller
public class UserController {

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

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createUser(HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        System.out.println(dataMap);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("account") || !dataMap.containsKey("pwd") ||
                !dataMap.containsKey("name") || !dataMap.containsKey("contact") ||
                !dataMap.containsKey("sex") || !dataMap.containsKey("intro")) {
            StringBuffer sb = new StringBuffer();
            if (!dataMap.containsKey("account"))
                sb.append(" account");
            if (!dataMap.containsKey("pwd"))
                sb.append(" pwd");
            if (!dataMap.containsKey("name"))
                sb.append(" name");
            if (!dataMap.containsKey("contact"))
                sb.append(" contact");
            if (!dataMap.containsKey("sex"))
                sb.append(" sex");
            if (!dataMap.containsKey("intro"))
                sb.append(" intro");
            return rd.falseSuccess("提供的参数不完整" + sb.toString()).buildReturnMap();
        }
        String account = (String) dataMap.get("account");
        String pwd = (String) dataMap.get("pwd");
        String name = (String) dataMap.get("name");
        String contact = (String) dataMap.get("contact");
        int sex = Integer.parseInt((String) dataMap.get("sex"));
        String intro = (String) dataMap.get("intro");
        User user = new User(account, pwd, name, contact, sex, intro);
        Long id = userService.createUser(user);
        if (id != -1L) {
            JSONObject data = new JSONObject();
            data.put("id", id);
            rd.trueSuccess().setData(data);
        } else {
            rd.falseSuccess("创建用户失败，原因未知");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> editUser(@PathVariable("userId") Long userId, HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("account") && !dataMap.containsKey("name")
                && !dataMap.containsKey("contact") && !dataMap.containsKey("sex")
                && !dataMap.containsKey("intro")) {
            return rd.falseSuccess("没有传递任何数据").buildReturnMap();
        }
        User user = userService.getUserById(userId);
        if (user == null) {
            return rd.falseSuccess("用户不存在").buildReturnMap();
        }
        if (dataMap.containsKey("account"))
            user.setAccount((String) dataMap.get("account"));
        if (dataMap.containsKey("name"))
            user.setName((String) dataMap.get("name"));
        if (dataMap.containsKey("contact"))
            user.setContact((String) dataMap.get("contact"));
        if (dataMap.containsKey("sex"))
            user.setSex(Integer.valueOf((String) dataMap.get("sex")));
        if (dataMap.containsKey("intro"))
            user.setIntro((String) dataMap.get("intro"));
        int res = userService.modifyUser(user);
        if (res >= 1) {
            rd.trueSuccess();
        } else {
            rd.falseSuccess("修改用户失败，未知原因");
        }
        return rd.buildReturnMap();
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUser(@PathVariable("userId") Long userId) {
        ReturnData rd = new ReturnData();
        User user = userService.getUserById(userId);
        if (user == null) {
            return rd.falseSuccess("用户不存在").buildReturnMap();
        }
        JSONObject data = new JSONObject();
        data.put("id", user.getId());
        data.put("account", user.getAccount());
        data.put("pwd", user.getPwd());
        data.put("name", user.getName());
        data.put("contact", user.getContact());
        data.put("sex", user.getSex());
        data.put("intro", user.getIntro());
        return rd.trueSuccess().setData(data).buildReturnMap();
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("account") || !dataMap.containsKey("pwd")) {
            return rd.falseSuccess("没有传递足够数据").buildReturnMap();
        }
        String account = (String) dataMap.get("account");
        String pwd = (String) dataMap.get("pwd");
        System.out.println("account => " + account + " ,pwd => " + pwd);
        User user = userService.getUserByAccount(account);
        if (user == null)
            return rd.falseSuccess("用户不存在").buildReturnMap();
        if (user.getPwd().equals(pwd)) {
            JSONObject jo = new JSONObject();
            jo.put("id", user.getId());
            return rd.trueSuccess().setData(jo).buildReturnMap();
        }
        return rd.falseSuccess("密码错误").buildReturnMap();
    }

}
