package com.cyh.scloud.payment.dao.repository;

import com.cyh.scloud.payment.dao.entity.ScloudPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/24  15:45
 * Description:
 */
@Repository
public interface PaymentRepository extends JpaRepository<ScloudPayment, Long> {

    @Query("select sp from ScloudPayment sp where sp.paymentNumber = :paymentNumber")
    ScloudPayment findByPayNumber(String paymentNumber);

}
