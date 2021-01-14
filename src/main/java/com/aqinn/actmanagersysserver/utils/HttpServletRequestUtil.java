package com.aqinn.actmanagersysserver.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpServletRequestUtil {

    public static Map<String,Object> getRequestParams(HttpServletRequest request){
        //参数定义
        String paraName = null;
        Map<String, Object> parameters = new HashMap<>();
        //获取请求参数并转换
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            paraName = enu.nextElement();
            parameters.put(paraName, request.getParameter(paraName));
        }
        return parameters;
    }

    public static int getInt(HttpServletRequest request, String key){
        try{
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request, String key){
        try{
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    public static Double getDouble(HttpServletRequest request, String key){
        try{
            return Double.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1d;
        }
    }

    public static boolean getBoolean(HttpServletRequest request, String key){
        try{
            return Boolean.valueOf(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }

    public static String getString(HttpServletRequest request, String key){
        try{
            String result = request.getParameter(key);
            if (result != null){
                //去掉左右两侧的空格
                result = result.trim();
            }
            if ("".equals(result)){
                result = null;
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }

}
