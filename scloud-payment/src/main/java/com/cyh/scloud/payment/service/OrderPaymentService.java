package com.cyh.scloud.payment.service;

import com.cyh.scloud.commonapi.response.ReturnResult;
import com.cyh.scloud.payment.dao.entity.ScloudPayment;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/24  15:48
 * Description:  订单支付接口
 */
public interface OrderPaymentService {

    /**
     * 生成支付流水
     * @return
     */
    ReturnResult<ScloudPayment> generatePayRecord(String orderNumber, String payer);

    /**
     * 查询支付流水
     * @param payNumber
     * @return
     */
    ReturnResult<ScloudPayment> getByPaymentNumber(String payNumber);
}
