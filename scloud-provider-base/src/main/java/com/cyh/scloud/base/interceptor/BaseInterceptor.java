package com.cyh.scloud.base.interceptor;

import com.cyh.scloud.base.common.BaseConstants;
import com.cyh.scloud.base.utils.NetUtils;
import com.cyh.scloud.base.utils.ThreadContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
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
        String ipAddress = NetUtils.getRequestIp(request);
        log.info("BaseInterceptor preHandle requestId={},ipAddress={}", requestId, ipAddress);
        if (StringUtils.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString().concat(ipAddress);
        }
        ThreadContextUtils.setRequestId(requestId);
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
    }
}
