package com.example.demo;

import com.example.demo.domain.Address;
import com.example.demo.domain.CreateRecipientRequest;
import com.example.demo.domain.RecipientAccountDetails;
import com.example.demo.domain.CreateQuoteRequest;
import com.example.demo.service.transferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(DemoApplication.class, args);

		Scanner scanner = new Scanner(System.in);

		final CreateQuoteRequest request = getCreateQuoteRequest(scanner);
		final var service = new transferService();
		final var tokenResponse = service.generateToken();

		// Sample code to parse the response and get any field
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(tokenResponse);
		JsonNode tokenNode =  mapper.readTree(json).get("access_token");
		final var token = tokenNode.textValue();

		final var response = service.createQuote(request, token);

		if(response == null) {
			System.out.println("Quote creation failed");
		}

		String stringJson = mapper.writeValueAsString(response);
		JsonNode jsonNode = mapper.readTree(stringJson);
		JsonNode idNode = jsonNode.get("id");
		final var quoteId = idNode.textValue();
		System.out.printf("Created quote id %s%n", quoteId);

		final var recipientResponse = createRecipient(scanner, request.getTargetCurrency(), service, token);
		JsonNode node = mapper.readTree(mapper.writeValueAsString(recipientResponse));
		final var recipientId = node.get("id").intValue();
		System.out.printf("Created recipient id %s%n", recipientId);

		service.updateTransfer(quoteId, recipientId, token);
		final var transferResponse = service.createTransfer(quoteId, recipientId, token);

		JsonNode transferNode = mapper.readTree(mapper.writeValueAsString(transferResponse));
		final var transferId = transferNode.get("id").intValue();
		System.out.printf("Transfer created successfully and transfer id is %s%n", transferId);
	}

	private static CreateQuoteRequest getCreateQuoteRequest(Scanner scanner) {
		System.out.println("From which currency would you like to transfer? Please enter the currency code. (e.g., GBP)");
		String sourceCurrency = scanner.next();

		System.out.println("Setting target currency to USD");
		System.out.printf("Creating a transfer %S -> USD%n", sourceCurrency);
		String targetCurrency = "USD";

		System.out.println("How much would you like to transfer? Please enter the amount in numbers (up to 50,000).");
		Long amount = scanner.nextLong();

		System.out.println("Creating a quote...");
		final var request = new CreateQuoteRequest();
		request.setSourceAmount(amount);
		request.setSourceCurrency(sourceCurrency);
		request.setTargetCurrency(targetCurrency);
		return request;
	}

	private static Object createRecipient(Scanner scanner, String currency, transferService service, String token) {
		System.out.println("Please enter recipient account details:");
		System.out.println("Enter account number:");
		String accountNumber = scanner.next();

		System.out.println("Enter email:");
		String email = scanner.next();

		System.out.println("Account type:(eg CHECKING)");
		String type = scanner.next();

		// account details are specific to USD
		System.out.println("Creating receiver account...");
		final var request = new CreateRecipientRequest();
		request.setCurrency(currency);
		request.setType("ABA");
		request.setAccountHolderName("receiver account" );
		final var details = new RecipientAccountDetails();
		details.setAccountNumber(accountNumber);
		details.setEmail(email);
		details.setAccountType(type);
		details.setAddress(new Address());
		request.setDetails(details);

		return service.createRecipient(request, token);
	}
}
