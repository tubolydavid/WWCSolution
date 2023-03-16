package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SenderAccountDetails {
    private String legalType = "PRIVATE";
    private String IBAN;
}
