package com.cyh.scloud.payment.dao.entity;

import com.cyh.scloud.payment.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/24  14:34
 * Description:
 */

@Entity
@Table(name = "scloud_payment")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Data
public class ScloudPayment implements Serializable {

    private static final long serialVersionUID = 6824336474804482783L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "`payment_number`", nullable = false, unique = true)
    private String paymentNumber;

    @Column(name = "`order_number`", nullable = false, unique = true)
    private String orderNumber;

    @Column(name = "`payer`", nullable = false, unique = true)
    private String payer;

    @Column(name = "`status`", nullable = false, unique = true)
    private PayStatusEnum status;
}
