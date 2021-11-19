package com.example.moneymanager.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponseWrapper<T> {
    private T data;

    public DataResponseWrapper(T data) {
        this.data = data;
    }
}
