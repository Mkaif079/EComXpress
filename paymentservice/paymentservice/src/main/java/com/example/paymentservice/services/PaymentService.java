package com.example.paymentservice.services;

import com.example.paymentservice.models.Payment;
import com.example.paymentservice.models.PaymentGateway;
import com.example.paymentservice.models.PaymentStatus;
import com.example.paymentservice.paymentgateways.PaymentGatewayFactory;
import com.example.paymentservice.paymentgateways.PaymentGatewayInterface;
import com.example.paymentservice.paymentgateways.RazorpayPaymentGateway;
import com.example.paymentservice.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final RazorpayPaymentGateway razorpayPaymentGateway;
    private PaymentGatewayFactory paymentGatewayFactory;

    private PaymentRepository paymentRepository;

    public PaymentService(PaymentGatewayFactory paymentGatewayFactory ,
                          PaymentRepository paymentRepository , RazorpayPaymentGateway razorpayPaymentGateway){
        this.paymentGatewayFactory = paymentGatewayFactory;
        this.paymentRepository = paymentRepository;
        this.razorpayPaymentGateway = razorpayPaymentGateway;
    }

    public String createPaymentLink(Long orderId){

        Long amount = 1000L;
        String userName = "Kaif";
        String userEmail = "abc@gmail.com";
        String userPhone = "+919999999999";

        PaymentGatewayInterface paymentGateway = paymentGatewayFactory.getBestPaymentGateway();

        String paymentLink = paymentGateway.createPaymentLink(
                amount , userName , userEmail , userPhone, orderId
        );

        Payment payment = new Payment();
        payment.setPaymentLink(paymentLink);
        payment.setOrderId(orderId);
        payment.setPaymentGateway(PaymentGateway.RAZORPAY);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setAmount(amount);

        paymentRepository.save(payment);
        return paymentLink;
    }

    public PaymentStatus getPaymentStatus(String paymentGatewayPaymentId){

        Payment payment = paymentRepository.findByPaymentGatewayReferenceId(paymentGatewayPaymentId);
        PaymentGatewayInterface paymentGateway = null;

        if(payment.getPaymentGateway().equals(PaymentGateway.RAZORPAY)){
            paymentGateway = razorpayPaymentGateway;
        }

        PaymentStatus paymentStatus = paymentGateway.getPaymentStatus(paymentGatewayPaymentId);

        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
        return paymentStatus;
    }
}
