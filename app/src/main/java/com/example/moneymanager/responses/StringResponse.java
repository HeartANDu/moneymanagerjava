package com.example.moneymanager.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringResponse {
    private final boolean success;
    private final String message;
}
