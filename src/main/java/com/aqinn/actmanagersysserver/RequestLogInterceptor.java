package com.aqinn.actmanagersysserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Aqinn
 * @Date 2020/12/24 11:27 上午
 */
public class RequestLogInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("【")
                .append(request.getMethod()).append("】")
                .append("接收到 IP 为: ")
                .append(request.getRemoteAddr())
                .append(" 的请求，请求 URL 为: ")
                .append(request.getRequestURL())
                .append("，响应状态码为: ")
                .append(response.getStatus())
                .append(e != null ? ("\n" + e.getMessage()) : "");
        logger.debug(sb.toString());
    }
}
