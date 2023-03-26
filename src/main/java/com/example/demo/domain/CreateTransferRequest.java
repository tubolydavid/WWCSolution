package com.example.demo.domain;

import lombok.Data;

@Data
public class CreateTransferRequest {
    int targetAccount;
    String quoteUuid;
    String customerTransactionId;
}
