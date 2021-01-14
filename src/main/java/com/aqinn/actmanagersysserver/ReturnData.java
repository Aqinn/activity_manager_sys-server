package com.aqinn.actmanagersysserver;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Aqinn
 * @Date 2020/12/23 4:56 下午
 */
public class ReturnData {

    private Map<String, Object> returnMap;

    public ReturnData() {
        returnMap = new HashMap<>();
    }

    public ReturnData trueSuccess() {
        this.returnMap.put("success", true);
        return this;
    }

    // 不应该使用这种方式，每一个 false 的响应都应该附上原因
    @Deprecated
    public ReturnData falseSuccess() {
        this.returnMap.put("success", false);
        return this;
    }
    @Deprecated
    public ReturnData setErrMsg(String errMsg) {
        this.returnMap.put("errMsg", errMsg);
        return this;
    }

    public ReturnData falseSuccess(String errMsg) {
        this.returnMap.put("success", false);
        this.returnMap.put("errMsg", errMsg);
        return this;
    }

    public ReturnData setData(Object o) {
        this.returnMap.put("data", o);
        return this;
    }

    public Map<String, Object> buildReturnMap() {
        if (!this.returnMap.containsKey("success")) {
            this.returnMap.clear();
            this.returnMap.put("success", false);
            this.returnMap.put("errMsg", "当你看到这行字，别担心。这代表接口返回数据格式出错，由后台背锅。");
            return this.returnMap;
        }
        return this.returnMap;
    }

}
