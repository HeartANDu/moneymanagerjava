package com.example.moneymanager.payload.request;

import com.example.moneymanager.accounting.TransactionAction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CreateOrUpdateTransactionRequest {
    @NotNull
    private Date date;

    @NotNull
    private TransactionAction action;

    @Positive
    private BigDecimal amount;

    @Size(max = 255)
    private String comment;

    @NotNull
    @JsonProperty("transaction_type_id")
    private Long transactionTypeId;

    @NotNull
    @JsonProperty("account_id")
    private Long accountId;
}
