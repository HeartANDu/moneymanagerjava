package com.example.moneymanager.test.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
    private final boolean success;
    private final String message;
}
