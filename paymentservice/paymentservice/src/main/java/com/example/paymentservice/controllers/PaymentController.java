package com.example.paymentservice.controllers;

import com.example.paymentservice.dtos.CreatePaymentLinkRequestDto;
import com.example.paymentservice.dtos.CreatePaymentLinkResponseDto;
import com.example.paymentservice.models.PaymentStatus;
import com.example.paymentservice.services.PaymentService;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments/")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping
    public CreatePaymentLinkResponseDto createPaymentLink(@RequestBody CreatePaymentLinkRequestDto request){
        String redirectUrl = this.paymentService.createPaymentLink(request.getOrderId());
        CreatePaymentLinkResponseDto response = new CreatePaymentLinkResponseDto();
        response.setUrl(redirectUrl);
        return response;
    }

    @GetMapping("/{id}")
    public PaymentStatus checkPaymentStatus(@PathVariable("id") String paymentGatewayPaymentId){
        return this.paymentService.getPaymentStatus(paymentGatewayPaymentId);
    }
}
