package com.example.paymentservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment extends BaseModel{
    private Long amount;
    private PaymentStatus paymentStatus;
    private Long userId;
    private Long orderId;
    private String paymentLink;
    private String paymentGatewayReferenceId;
    private PaymentGateway paymentGateway;
}
