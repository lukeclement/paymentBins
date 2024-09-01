package org.lukario.controller;

import org.lukario.model.dto.BucketDto;
import org.lukario.model.dto.TimeWindowDto;
import org.lukario.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BucketController {
    private final BucketService bucketService;

    @Autowired
    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/buckets")
    public List<BucketDto> get() {
        return new ArrayList<>();
    }

    @PostMapping("/bucket")
    public BucketDto add(
            @RequestParam("Name") String name,
            @RequestParam("Payment") Double payment,
            @RequestParam("Target") Double target,
            @RequestParam("PaymentRate") String paymentRate,
            @RequestParam(value = "PaymentRateFrequency", required = false) Double paymentRateFrequency
    ) {
        return bucketService.add(name, payment, target, paymentRate, paymentRateFrequency);
    }
}
