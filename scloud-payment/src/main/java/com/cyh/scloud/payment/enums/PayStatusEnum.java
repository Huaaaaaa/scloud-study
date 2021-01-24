package com.cyh.scloud.payment.enums;

import org.springframework.data.domain.PageRequest;

import java.util.Arrays;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/24  17:06
 * Description:
 */
public enum PayStatusEnum {

    PAY_FAILED(0, "支付失败"),
    PAY_SUCCESS(1, "支付成功");

    private int status;

    private String mesage;


    PayStatusEnum(int status, String mesage) {
        this.status = status;
        this.mesage = mesage;
    }

    public int getStatus() {
        return status;
    }

    public String getMesage() {
        return mesage;
    }

    public static PayStatusEnum getByStatus(int status){
        return Arrays.stream(PayStatusEnum.values()).filter(payStatusEnum -> payStatusEnum.getStatus()==status).findFirst().get();
    }
}
