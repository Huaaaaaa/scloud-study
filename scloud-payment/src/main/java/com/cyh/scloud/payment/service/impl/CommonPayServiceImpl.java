package com.cyh.scloud.payment.service.impl;

import com.cyh.scloud.commonapi.response.ReturnResult;
import com.cyh.scloud.payment.dao.entity.ScloudPayment;
import com.cyh.scloud.payment.dao.repository.PaymentRepository;
import com.cyh.scloud.payment.service.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/24  15:51
 * Description:
 */
@Service
public class CommonPayServiceImpl implements OrderPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * 生成支付流水
     *
     * @return
     */
    @Override
    public ReturnResult<ScloudPayment> generatePayRecord(String orderNumber, String payer) {
        ScloudPayment sp = new ScloudPayment();
        sp.setPaymentNumber(UUID.randomUUID().toString().replaceAll("-", ""));
        sp.setOrderNumber(orderNumber);
        sp.setPayer(payer);
        ScloudPayment res = paymentRepository.save(sp);
        ReturnResult<ScloudPayment> result = new ReturnResult<>();
        if (res != null) {
            return result.okResult(res);
        }
        return result.errorResult();
    }


    /**
     * 查询支付流水
     *
     * @param payNumber
     * @return
     */
    @Override
    public ReturnResult<ScloudPayment> getByPaymentNumber(String payNumber) {
        ScloudPayment sp = paymentRepository.findByPayNumber(payNumber);
        return new ReturnResult().okResult(sp);
    }
}
