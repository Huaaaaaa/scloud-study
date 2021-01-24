package com.cyh.scloud.payment.interceptor;

import com.cyh.scloud.commonapi.common.BaseConstants;
import com.cyh.scloud.commonapi.utils.NetUtils;
import com.cyh.scloud.commonapi.utils.ThreadContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/21  17:43
 * Description:
 */
@Slf4j
public class BaseInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestId = request.getHeader(BaseConstants.HEADER_REQUEST_ID);
        String user = request.getHeader(BaseConstants.HEADER_USER);
        if (StringUtils.isEmpty(user)) {
            log.error("user can not be empty.");
            return false;
        }
        String ipAddress = NetUtils.getRequestIp(request);
        log.info("BaseInterceptor preHandle requestId={},ipAddress={}", requestId, ipAddress);
        if (StringUtils.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString().concat(ipAddress);
        }
        ThreadContextUtils.setRequestId(requestId);
        ThreadContextUtils.setUser(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        ThreadContextUtils.removeCache(BaseConstants.HEADER_REQUEST_ID);
        ThreadContextUtils.removeCache(BaseConstants.HEADER_USER);
    }
}
