package com.example.product.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DummyController {

    private RestTemplate restTemplate;

    public DummyController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @RequestMapping("/dummy")
    public void dummyApi(){
        String answer = restTemplate.getForObject("http://userservice/hi" ,String.class);

        System.out.println(answer);
    }
}
