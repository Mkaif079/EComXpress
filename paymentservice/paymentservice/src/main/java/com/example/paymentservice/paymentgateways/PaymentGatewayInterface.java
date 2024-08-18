package com.example.paymentservice.paymentgateways;

import com.example.paymentservice.models.PaymentStatus;

public interface PaymentGatewayInterface {

    String createPaymentLink(
            Long amount,
            String userName,
            String userEmail,
            String userPhone,
            Long orderId
    );
    PaymentStatus getPaymentStatus(
            String paymentId
    );
}
