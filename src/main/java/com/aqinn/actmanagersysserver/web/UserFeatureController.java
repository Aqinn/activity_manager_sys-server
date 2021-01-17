package com.aqinn.actmanagersysserver.web;

import com.alibaba.fastjson.JSONObject;
import com.aqinn.actmanagersysserver.RequestLogInterceptor;
import com.aqinn.actmanagersysserver.ReturnData;
import com.aqinn.actmanagersysserver.entity.*;
import com.aqinn.actmanagersysserver.service.*;
import com.aqinn.actmanagersysserver.utils.HttpServletRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Aqinn
 * @Date 2021/1/15 1:41 下午
 */
@Controller
public class UserFeatureController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFeatureService userFeatureService;

    @Autowired
    private ActService actService;

    @Autowired
    private AttendService attendService;

    @Autowired
    private UserAttendService userAttendService;

    private static final Logger logger = LoggerFactory.getLogger(UserFeatureController.class);
    private final double threshold = 0.5;
    public static HashMap<Long, List<UserFeature>> actFeatures = new HashMap<>();
    private static final Object checkLock = new Object();

    @RequestMapping(value = "/userfeature/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> collectFace(@PathVariable("userId") Long userId, HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("f1") || !dataMap.containsKey("f2") ||
                !dataMap.containsKey("f3") || !dataMap.containsKey("f4")) {
            return rd.falseSuccess("没有传递足够数据").buildReturnMap();
        }
        String f1 = (String) dataMap.get("f1");
        String f2 = (String) dataMap.get("f2");
        String f3 = (String) dataMap.get("f3");
        String f4 = (String) dataMap.get("f4");
        String[] f1Arr = f1.split(",");
        String[] f2Arr = f2.split(",");
        String[] f3Arr = f3.split(",");
        String[] f4Arr = f4.split(",");
        if (f1Arr.length != 128 || f2Arr.length != 128 ||
                f3Arr.length != 128 || f4Arr.length != 128)
            return rd.falseSuccess("人脸特征数量错误，应该是 128 维向量").buildReturnMap();
        try {
            for (String s : f1Arr) {
                Float.valueOf(s);
            }
            for (String s : f2Arr) {
                Float.valueOf(s);
            }
            for (String s : f3Arr) {
                Float.valueOf(s);
            }
            for (String s : f4Arr) {
                Float.valueOf(s);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return rd.falseSuccess("人脸特征格式错误").buildReturnMap();
        }
        userFeatureService.deleteAllFeatures(userId);
        if (userFeatureService.createFeatures(userId, f1, f2, f3, f4) == 0)
            return rd.trueSuccess().buildReturnMap();
        else
            return rd.falseSuccess("人脸特征插入数据库过程中出错").buildReturnMap();
    }

    @RequestMapping(value = "/userfeature/check/{attendId}/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> singleFaceRecognize(@PathVariable("attendId") Long attendId, @PathVariable("userId") Long userId, HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("feature")) {
            return rd.falseSuccess("没有传递足够数据").buildReturnMap();
        }
        Attend attend = attendService.getAttendById(attendId);
        if (attend == null)
            return rd.falseSuccess("签到不存在").buildReturnMap();
        if (!attend.getIsOpen().equals(2))
            return rd.falseSuccess("签到并未进行中").buildReturnMap();
        Act act = actService.getActById(attend.getActId());
        if (act == null)
            return rd.falseSuccess("签到所对应的活动不存在，后台背锅").buildReturnMap();
        Long actId = act.getId();
        String feature = (String) dataMap.get("feature");
        String[] fArr = feature.split(",");
        if (fArr.length != 128)
            return rd.falseSuccess("人脸特征数量错误，应该是 128 维向量").buildReturnMap();
        float[] ff = new float[128];
        try {
            for (int i = 0; i < fArr.length; i++) {
                ff[i] = Float.parseFloat(fArr[i]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return rd.falseSuccess("人脸特征格式错误").buildReturnMap();
        }
        boolean res = checkSingleFace(actId, userId, ff);
        if (res) {
            // 签到
            synchronized (checkLock) {
                UserAttend temp = userAttendService.getUserAttend(userId, attendId);
                if (temp != null)
                    return rd.falseSuccess("该人脸已经签到过了，请勿重复签到").buildReturnMap();
                UserAttend userAttend = new UserAttend(userId, attendId, System.currentTimeMillis(),2);
                userAttendService.attend(userAttend);
            }
            User user = userService.getUserById(userId);
            return rd.trueSuccess().setData("账号:"+user.getAccount() + ", 昵称:" + user.getName()+"（自助签到）").buildReturnMap();
        } else {
            return rd.falseSuccess("签到失败，该人脸信息并未在签到列表中").buildReturnMap();
        }
    }

    @RequestMapping(value = "/userfeature/videocheck", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> videoFrameFaceRecognize(HttpServletRequest request) {
        Map<String, Object> dataMap = HttpServletRequestUtil.getRequestParams(request);
        ReturnData rd = new ReturnData();
        if (!dataMap.containsKey("feature") || !dataMap.containsKey("attendId")) {
            return rd.falseSuccess("没有传递足够数据").buildReturnMap();
        }
        Long attendId = Long.valueOf(String.valueOf(dataMap.get("attendId")));
        String feature = (String) dataMap.get("feature");
        Attend attend = attendService.getAttendById(attendId);
        if (attend == null)
            return rd.falseSuccess("签到不存在").buildReturnMap();
        if (!attend.getIsOpen().equals(2))
            return rd.falseSuccess("签到并未进行中").buildReturnMap();
        Act act = actService.getActById(attend.getActId());
        if (act == null)
            return rd.falseSuccess("签到所对应的活动不存在，后台背锅").buildReturnMap();
        Long actId = act.getId();
        if (!actFeatures.containsKey(actId)) {
            actFeatures.put(actId, userFeatureService.queryUserFeaturesByActId(actId));
        }
        String[] fArr = feature.split(",");
        if (fArr.length != 128)
            return rd.falseSuccess("人脸特征数量错误，应该是 128 维向量").buildReturnMap();
        float[] ff = new float[128];
        try {
            for (int i = 0; i < fArr.length; i++) {
                ff[i] = Float.parseFloat(fArr[i]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return rd.falseSuccess("人脸特征格式错误").buildReturnMap();
        }
        Long res = checkVideoFace(actId, ff);
        if (res > 0L) {
            // 签到
            int attendRes;
            synchronized (checkLock) {
                UserAttend temp = userAttendService.getUserAttend(res, attendId);
                if (temp != null)
                    return rd.falseSuccess("该人脸已经签到过了，请勿重复签到").buildReturnMap();
                UserAttend userAttend = new UserAttend(res, attendId, System.currentTimeMillis(), 1);
                attendRes = userAttendService.attend(userAttend);
            }
            logger.debug("attendRes: " + attendRes);
//            if (attendRes)
            User user = userService.getUserById(res);
            return rd.trueSuccess().setData("账号:"+user.getAccount() + ", 昵称:" + user.getName()+"（视频签到）").buildReturnMap();
        } else {
            return rd.falseSuccess("签到失败，该人脸信息并未在签到列表中").buildReturnMap();
        }
    }

    public static void forceRefreshActFeatures(Long actId, UserFeatureService service) {
        actFeatures.put(actId, service.queryUserFeaturesByActId(actId));
    }

    private Long checkVideoFace(Long actId, float[] features) {
        if (!actFeatures.containsKey(actId)) {
            actFeatures.put(actId, userFeatureService.queryUserFeaturesByActId(actId));
        }
        List<UserFeature> list = actFeatures.get(actId);
        logger.debug(list.toString());
        for (UserFeature uf : list) {
            String[] fArr = uf.getFeature().split(",");
            if (fArr.length != 128) {
                return -1L;
            }
            float[] ff = new float[128];
            try {
                for (int i = 0; i < fArr.length; i++) {
                    ff[i] = Float.parseFloat(fArr[i]);
                }
                float similar = compare(ff, features, 3);
                if (similar > threshold) {
                    logger.debug("similar: " + similar);
                    return uf.getuId();
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug(e.getMessage());
                return -2L;
            }
        }
        return -3L;
    }

    private boolean checkSingleFace(Long actId, Long userId, float[] features) {
        if (!actFeatures.containsKey(actId)) {
            actFeatures.put(actId, userFeatureService.queryUserFeaturesByActId(actId));
        }
        List<UserFeature> list = actFeatures.get(actId);
        logger.debug(list.toString());
        for (UserFeature uf : list) {
            String[] fArr = uf.getFeature().split(",");
            if (fArr.length != 128) {
                return false;
            }
            float[] ff = new float[128];
            try {
                for (int i = 0; i < fArr.length; i++) {
                    ff[i] = Float.parseFloat(fArr[i]);
                }
                float similar = compare(ff, features, 3);
                if (similar > threshold && uf.getuId().equals(userId)) {
                    logger.debug("similar: " + similar);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug(e.getMessage());
                return false;
            }
        }
        return false;
    }

    private float compare(float[] feature1, float[] feature2, int distance) {
        if (feature1 == null || feature2 == null)
            return -1001;
        if (feature1.length != 128 || feature2.length != 128)
            return -1002;
        if (distance != 1 && distance != 2 && distance != 3)
            return -1003;
        if (distance == 1)
            return Eucledian(feature1, feature2);
        if (distance == 2)
            return Manhattan(feature1, feature2);
        if (distance == 3)
            return CosineSimilarity(feature1, feature2);
        return -1000;
    }

    private float Eucledian(float[] a, float[] b) {
        float sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return (float) (Math.sqrt(sum));
    }

    private float Manhattan(float[] a, float[] b) {
        float sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.abs(a[i] - b[i]);
        }
        return sum;
    }

    private float CosineSimilarity(float[] a, float[] b) {
        return (float) (MultiplicationAndAdd(a, b) / (Math.sqrt(MultiplicationAndAdd(a, a)) * Math.sqrt(MultiplicationAndAdd(b, b))));
    }

    private float MultiplicationAndAdd(float[] a, float[] b) {
        float sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }

}
