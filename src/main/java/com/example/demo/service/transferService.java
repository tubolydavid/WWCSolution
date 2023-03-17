package com.example.demo.service;

import com.example.demo.domain.CreateQuoteRequest;
import com.example.demo.domain.CreateRecipientRequest;
import com.example.demo.domain.CreateSenderAcctRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
    import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class transferService {
    public Object generateToken(String basicAuth, String refreshToken) {
        return WebClient.builder().build().post()
                .uri("https://api.sandbox.transferwise.tech/oauth/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header(HttpHeaders.AUTHORIZATION, String.format("Basic %s", basicAuth)) // include basic auth token here
                .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                        .with("refresh_token", refreshToken)) // include refresh token here
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class).block();
    }
    public Object createQuote(CreateQuoteRequest request) {
        return WebClient.builder().build().post()
                .uri("https://api.sandbox.transferwise.tech/v2/quotes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer <token>")
                .body(Mono.just(request), CreateQuoteRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class).block();
    }

    public Object createSenderAcct(CreateSenderAcctRequest request) {
        return WebClient.builder().build().post()
                .uri("https://api.sandbox.transferwise.tech/v1/accounts")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer <token>")
                .body(Mono.just(request), CreateSenderAcctRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class).block();
    }

    public Object createRecipient(CreateRecipientRequest request) {
        return WebClient.builder().build().post()
                .uri("https://api.sandbox.transferwise.tech/v1/accounts")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer <token>")
                .body(Mono.just(request), CreateRecipientRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class).block();
    }
}
