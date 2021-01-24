package com.cyh.scloud.payment.controller;

import com.cyh.scloud.commonapi.response.ReturnResult;
import com.cyh.scloud.commonapi.utils.ThreadContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/24  16:49
 * Description:
 */
public class BaseController {

    protected ReturnResult setResult(ReturnResult result) {
        result.setRequestId(ThreadContextUtils.getRequestId());
        return result;
    }


    protected String getUser() {
        return ThreadContextUtils.getUser();
    }
}
