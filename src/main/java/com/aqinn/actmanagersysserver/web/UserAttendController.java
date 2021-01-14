package com.aqinn.actmanagersysserver.web;

import com.aqinn.actmanagersysserver.ReturnData;
import com.aqinn.actmanagersysserver.entity.UserAttend;
import com.aqinn.actmanagersysserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
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
        UserAttend userAttend = new UserAttend(userId, attendId, new Date().toString(), attendType);
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

}
