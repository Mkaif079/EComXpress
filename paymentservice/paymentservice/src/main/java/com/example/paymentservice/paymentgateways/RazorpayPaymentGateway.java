package com.example.paymentservice.paymentgateways;

import com.example.paymentservice.models.PaymentStatus;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayPaymentGateway implements PaymentGatewayInterface{

    private RazorpayClient razorpayClient;

    public RazorpayPaymentGateway(RazorpayClient razorpayClient){
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String createPaymentLink(Long amount, String userName,
                                    String userEmail, String userPhone , Long orderId) {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
//        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",System.currentTimeMillis()/1000+30*60);
        paymentLinkRequest.put("reference_id",orderId.toString());
//        paymentLinkRequest.put("description","Payment for policy no #23456");
        JSONObject customer = new JSONObject();
        customer.put("name",userPhone);
        customer.put("contact",userName);
        customer.put("email",userEmail);
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
//        JSONObject notes = new JSONObject();
//        notes.put("policy_name","Jeevan Bima");
//        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://scaler.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = null;
        try {
            payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        }
        catch (RazorpayException razorpayException){
            System.out.println("Something went wrong");
        }
        return payment.get("short_url");
    }

    @Override
    public PaymentStatus getPaymentStatus(String paymentId) {
        Payment payment = null;
        try{
            payment = razorpayClient.payments.fetch(paymentId);
        }
        catch (RazorpayException razorpayException){
            System.out.println(razorpayException);
        }
        String paymentStatus = payment.get("status");

        if(paymentStatus.equals("captured")){
            return PaymentStatus.SUCCESS;
        }
        else if(paymentStatus.equals("failed")){
            return PaymentStatus.FAILURE;
        }
        return null;
    }
}
