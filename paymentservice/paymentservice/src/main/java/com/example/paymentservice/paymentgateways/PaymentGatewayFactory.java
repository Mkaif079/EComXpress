package com.example.paymentservice.paymentgateways;

import com.razorpay.RazorpayClient;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class PaymentGatewayFactory {

    private RazorpayPaymentGateway razorpayPaymentGateway;

    public PaymentGatewayFactory(RazorpayPaymentGateway razorpayPaymentGateway){
        this.razorpayPaymentGateway = razorpayPaymentGateway;
    }
    public PaymentGatewayInterface getBestPaymentGateway(){

        //int random = new Random().nextInt();

//        if(random %2 == 0){
//            return new RazorpayPaymentGateway();
//        }
//        return new StripePaymentGateway();

        return razorpayPaymentGateway;
    }
}
