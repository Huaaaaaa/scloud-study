package com.cyh.scloud.commonapi.utils;

import com.cyh.scloud.commonapi.common.BaseConstants;
import org.apache.logging.log4j.ThreadContext;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/21  18:45
 * Description: 线程上下文工具，设置一些线程私有数据
 */
public class ThreadContextUtils {

    public static void setRequestId(String requestId) {
        ThreadContext.put(BaseConstants.HEADER_REQUEST_ID, requestId);
    }

    public static String getRequestId() {
        return ThreadContext.get(BaseConstants.HEADER_REQUEST_ID);
    }

    public static void setUser(String user) {
        ThreadContext.put(BaseConstants.HEADER_USER, user);
    }

    public static String getUser() {
        return ThreadContext.get(BaseConstants.HEADER_USER);
    }

    public static void removeCache(String key) {
        ThreadContext.remove(key);
    }
}
