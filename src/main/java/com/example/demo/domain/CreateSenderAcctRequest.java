package com.example.demo.domain;

import lombok.Data;

@Data
public class CreateSenderAcctRequest {
    String accountHolderName;
    String currency;
    String type;
    SenderAccountDetails details;
    Long profile = 16727665L;
}
