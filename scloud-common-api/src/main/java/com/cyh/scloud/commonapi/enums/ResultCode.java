package com.cyh.scloud.commonapi.enums;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/24  16:10
 * Description: 返回状态码
 */

public enum ResultCode {

    SUCCESS(200, "Successful"),
    ERROR(500, "Internal Error");


    private int code;

    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
