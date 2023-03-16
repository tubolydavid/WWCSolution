package com.example.demo;

import com.example.demo.domain.Address;
import com.example.demo.domain.CreateRecipientRequest;
import com.example.demo.domain.RecipientAccountDetails;
import com.example.demo.domain.SenderAccountDetails;
import com.example.demo.domain.CreateQuoteRequest;
import com.example.demo.domain.CreateSenderAcctRequest;
import com.example.demo.service.transferService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		Scanner scanner = new Scanner(System.in);

		final CreateQuoteRequest request = getCreateQuoteRequest(scanner);
		final var service = new transferService();
		final var response = service.createQuote(request);

		if(response == null) {
			System.out.println("Quote creation failed");
		}

		createSenderAcct(scanner, request.getSourceCurrency(), service);
		createRecipient(scanner, request.getTargetCurrency(), service);
	}

	private static CreateQuoteRequest getCreateQuoteRequest(Scanner scanner) {
		System.out.println("From which currency would you like to transfer? Please enter the currency code. (e.g., GBP)");
		String sourceCurrency = scanner.next();

		System.out.println("What currency do you want to transfer to (EUR)?");
		String targetCurrency = scanner.next();

		System.out.println("How much would you like to transfer? Please enter the amount in numbers (up to 50,000).");
		Long amount = scanner.nextLong();

		System.out.println("Creating a quote...");
		final var request = new CreateQuoteRequest();
		request.setSourceAmount(amount);
		request.setSourceCurrency(sourceCurrency);
		request.setTargetCurrency(targetCurrency);
		return request;
	}

	private static void createSenderAcct(Scanner scanner, String currency, transferService service) {
		System.out.println("Please enter your account number");
		String accountNumber = scanner.next();

		System.out.println("Creating sender account...");
		final var request = new CreateSenderAcctRequest();
		request.setCurrency(currency);
		request.setType("iban");
		request.setAccountHolderName("Sender account" );
		final var details = new SenderAccountDetails();
		details.setIBAN(accountNumber);
		request.setDetails(details);

		final var createSenderResponse = service.createSenderAcct(request);

		System.out.println("SUCCESSFUL!");
	}

	private static void createRecipient(Scanner scanner, String currency, transferService service) {
		System.out.println("Please enter recipient account details:");
		System.out.println("Account number");
		String accountNumber = scanner.next();

		System.out.println("Email");
		String email = scanner.next();

		System.out.println("Account type:(eg CHECKING)");
		String type = scanner.next();

		System.out.println("Creating sender account...");
		final var request = new CreateRecipientRequest();
		request.setCurrency(currency);
		request.setType("ABA");
		request.setAccountHolderName("Sender account" );
		final var details = new RecipientAccountDetails();
		details.setAccountNumber(accountNumber);
		details.setEmail(email);
		details.setAccountType(type);
		details.setAddress(new Address());
		request.setDetails(details);

		final var createSenderResponse = service.createRecipient(request);

		System.out.println("SUCCESSFUL!");
	}
}
