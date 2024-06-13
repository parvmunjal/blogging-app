package com.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
@Configuration
public class SwaggerConfig {
	
	
	 @Bean
	    public OpenAPI openAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("Blogging App API")
	                        .description("Learning REST APIs")
	                        .version("1.0")
	                        .contact(new Contact().name("Parv Munjal").email("parvmunjal100@gmail.com"))
	                        .license(new License().name("Apache License").url("https://www.apache.org/licenses/LICENSE-2.0")))
	                .externalDocs(new ExternalDocumentation().description("External Url"))
	                .addSecurityItem(new SecurityRequirement().addList("JWT"))
	                .components(new Components().addSecuritySchemes("JWT",
	                        new SecurityScheme()
	                                .name("JWT")
	                                .type(SecurityScheme.Type.HTTP)
	                                .scheme("bearer")
	                                .bearerFormat("JWT")));
	    }
	
	
	
//	private static final String AUTHORIZATION_HEADER="Authorization";
//	
//	public ApiKey apiKeys() {
//		return new ApiKey("JWT",AUTHORIZATION_HEADER, "header");
//	}
//	
//	private List<SecurityContext> securityContexts(){
//		return Arrays.asList(SecurityContext.builder().securityReferences(references()).build());
//	}
//	private List<SecurityReference> references() {
//		AuthorizationScope scopes=new AuthorizationScope("global", "accessEverything");
//		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scopes}));
//	}

}
