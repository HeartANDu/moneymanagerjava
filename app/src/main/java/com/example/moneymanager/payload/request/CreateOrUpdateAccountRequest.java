package com.example.moneymanager.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateOrUpdateAccountRequest {
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotNull(message = "Type should be specified")
    @JsonProperty("account_type_id")
    private Integer accountTypeId;
}
