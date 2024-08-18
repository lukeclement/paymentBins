package org.lukario.controller;

import org.lukario.model.dto.BucketDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@RestController
public class BucketController {

    @GetMapping("/buckets")
    public List<BucketDto> getBuckets() {
        throw new UnsupportedOperationException();
    }
}
