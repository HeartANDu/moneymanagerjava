package com.example.moneymanager.controllers;

import com.example.moneymanager.payload.response.MessageResponse;
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
        return ResponseEntity.ok(new MessageResponse("Testing functionality"));
    }

    @GetMapping("/adminmessage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> authorizedMessage() {
        return ResponseEntity.ok(new MessageResponse("Testing authorized functionality"));
    }
}
