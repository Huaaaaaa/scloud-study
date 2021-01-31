package com.cyh.scloud.consumer.service;

/**
 * @author: cyhua
 * @createTime: 2021/1/31
 * @description:
 */
public class CommonService {

    /**
     * 降低处理
     *
     * @return
     */
    public String commonFullback() {
        return "Internal Error";
    }

    /**
     * 降级处理并返回触发降低的异常信息
     *
     * @param e
     * @return
     */
    public String commonFullbackAndReturnException(Throwable e) {
        return "Internal Error,e=" + e.getMessage();
    }
}
