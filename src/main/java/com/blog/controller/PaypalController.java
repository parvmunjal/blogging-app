package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.services.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/paypal")
@RequiredArgsConstructor
public class PaypalController {

	@Autowired
	private PaypalService paypalService;
	
	public static final String SUCCESS_URL="/api/paypal/pay/success";
	public static final String CANCEL_URL="/api/paypal/pay/cancel";
	
	@PostMapping("/pay")
	public ResponseEntity<ApiResponse> payment(@RequestParam double sum){
		try {
			Payment payment=paypalService.createPayment(
					sum,
					"USD",
					"paypal",
					"sale",
					"Test payment",
					"http://localhost:8080"+CANCEL_URL,
					"http://localhost:8080"+SUCCESS_URL
					);
			
			for(Links links:payment.getLinks()) {
				if(links.getRel().equals("approval_url")) {
				return ResponseEntity.ok(new ApiResponse("Redirect to Paypal for approval:"+links.getHref(), true));
			}
		}
	}		
		catch(PayPalRESTException e) {
			e.printStackTrace();
			return new ResponseEntity<ApiResponse>
			(new ApiResponse("Error occured while creating payment",false),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiResponse>
		(new ApiResponse("Error occured while creating payment",false),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value=CANCEL_URL)
	public ResponseEntity<ApiResponse> cancelPayment(){
		return ResponseEntity.ok(new ApiResponse("Payment cancelled", false));
	}
	
	@GetMapping(value=SUCCESS_URL)
	public ResponseEntity<ApiResponse> successPayment(@RequestParam String paymentId, @RequestParam String payerId){
		try {
			Payment payment=paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")) {
				
				return ResponseEntity.ok(new ApiResponse("Payment Successful", true));
			}
		}
		catch(PayPalRESTException e) {
			e.printStackTrace();
			return new ResponseEntity<ApiResponse>
			(new ApiResponse("Error occured while executing payment",false),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiResponse>
		(new ApiResponse("Error occured while executing payment",false),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
