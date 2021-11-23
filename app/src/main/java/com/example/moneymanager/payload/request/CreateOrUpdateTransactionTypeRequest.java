package com.example.moneymanager.payload.request;

import com.example.moneymanager.accounting.TransactionAction;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateOrUpdateTransactionTypeRequest {
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    private TransactionAction action;
}
