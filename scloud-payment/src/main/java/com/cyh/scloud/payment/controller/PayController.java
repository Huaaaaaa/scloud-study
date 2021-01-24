package com.cyh.scloud.payment.controller;

import com.cyh.scloud.commonapi.response.ReturnResult;
import com.cyh.scloud.payment.service.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/24  16:05
 * Description:
 */
@RestController
@RequestMapping("/pay")
public class PayController extends BaseController {

    @Autowired
    private OrderPaymentService orderPaymentService;

    /**
     * 支付订单
     * @param orderNumber
     * @return
     */
    @GetMapping("/index")
    @ResponseBody
    public ReturnResult index() {
        return new ReturnResult().okResult("I‘m alive");
    }

    /**
     * 支付订单
     * @param orderNumber
     * @return
     */
    @GetMapping("/payOrder/{orderNumber}")
    @ResponseBody
    public ReturnResult payOrder(@PathVariable String orderNumber) {
        return setResult(orderPaymentService.generatePayRecord(orderNumber, getUser()));
    }

    /**
     * 获取支付结果
     * @param paymentNumber
     * @return
     */
    @GetMapping("/getPayResult/{paymentNumber}")
    @ResponseBody
    public ReturnResult getPayResult(@PathVariable String paymentNumber) {
        return setResult(orderPaymentService.getByPaymentNumber(paymentNumber));
    }
}
