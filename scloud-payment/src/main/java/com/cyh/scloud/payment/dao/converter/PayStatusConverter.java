package com.cyh.scloud.payment.dao.converter;

import com.cyh.scloud.payment.enums.PayStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/24  17:13
 * Description: 支付状态枚举转换器
 */
@Converter(autoApply = true) //autoApply为true表示这个转换器被自动应用到所有的entity
public class PayStatusConverter implements AttributeConverter<PayStatusEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PayStatusEnum payStatusEnum) {
        if (payStatusEnum != null) {
            return payStatusEnum.getStatus();
        }
        return null;
    }

    @Override
    public PayStatusEnum convertToEntityAttribute(Integer status) {
        PayStatusEnum statusEnum = PayStatusEnum.getByStatus(status);
        if (statusEnum == null) {
            throw new IllegalArgumentException("无效的支付状态");
        }
        return statusEnum;
    }
}
