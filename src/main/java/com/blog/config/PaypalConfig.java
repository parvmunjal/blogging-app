package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;

@Configuration
public class PaypalConfig {

	private String clientId="AXuhcR5DvI3l_1yPgSefCFnLm8haQ56qDd6BqhjyNdbLJZg6nG4Kl1fThng7lqhdhaoSIDX3Cp1xCYyA";
	private String clientSecret="EFDUmO_jNvYCxDhbJObRIyKQuvS1EDGTLmniOnsku9FOcgQaBZtMWuF5kl5mX5W9PkvIhtn4glPqjMDg";
	private String mode="sandbox";
	
	@Bean
	public APIContext apiContext() {
		return new APIContext(clientId,clientSecret,mode);
	}
}
