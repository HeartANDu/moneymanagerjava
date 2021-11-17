package com.example.moneymanager.controllers;

import com.example.moneymanager.responses.StringResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/message")
    public StringResponse message() {
        return new StringResponse(true, "Testing functionality");
    }
}
