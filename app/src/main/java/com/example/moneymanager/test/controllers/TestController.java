package com.example.moneymanager.test.controllers;

import com.example.moneymanager.test.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/message")
    public ResponseEntity<?> message() {
        return ResponseEntity.ok(new MessageResponse(true, "Testing functionality"));
    }

    @GetMapping("/authmessage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> authorizedMessage() {
        return ResponseEntity.ok(new MessageResponse(true, "Testing authorized functionality"));
    }
}
