package com.example.paymentservice.paymentgateways;

import com.example.paymentservice.models.PaymentStatus;

public class StripePaymentGateway implements PaymentGatewayInterface{
    @Override
    public String createPaymentLink(Long amount, String userName, String userEmail, String userPhone , Long orderId) {
        return "";
    }

    @Override
    public PaymentStatus getPaymentStatus(String paymentId) {
        return null;
    }
}
