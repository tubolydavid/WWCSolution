package com.example.demo.service;

import com.example.demo.domain.CreateQuoteRequest;
import com.example.demo.domain.CreateRecipientRequest;
import com.example.demo.domain.CreateTransferRequest;
import com.example.demo.domain.UpdateQuoteRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class transferService {
    public Object generateToken() {
        return WebClient.builder().build().post()
                .uri("https://api.sandbox.transferwise.tech/oauth/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Basic dHctdGVzdC1iYW5rOnR3LXRlc3QtYmFuaw==")
                .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                        .with("refresh_token", "efd76f1c-f819-4138-b5f1-2fe69512cec7"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class).block();
    }
    public Object createQuote(CreateQuoteRequest request, String token) {
        return WebClient.builder().build().post()
                .uri("https://api.sandbox.transferwise.tech/v2/quotes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .body(Mono.just(request), CreateQuoteRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class).block();
    }

    public Object createRecipient(CreateRecipientRequest request, String token) {
        return WebClient.builder().build().post()
                .uri("https://api.sandbox.transferwise.tech/v1/accounts")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .body(Mono.just(request), CreateRecipientRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class).block();
    }

    public Object updateTransfer(String quoteId, int recipientId, String token) {
        final UpdateQuoteRequest request = new UpdateQuoteRequest();
        request.setTargetAccount(recipientId);

        return WebClient.builder().build().patch()
                .uri("https://api.sandbox.transferwise.tech/v2/quotes/".concat(quoteId))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .body(Mono.just(request), UpdateQuoteRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class).block();
    }

    public Object createTransfer(String quoteId, int recipientId, String token) {
        final CreateTransferRequest request = new CreateTransferRequest();
        request.setTargetAccount(recipientId);
        request.setQuoteUuid(quoteId);
        request.setCustomerTransactionId(String.valueOf(UUID.randomUUID()));

        return WebClient.builder().build().post()
                .uri("https://api.sandbox.transferwise.tech/v1/transfers")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .body(Mono.just(request), CreateTransferRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class).block();
    }
}
