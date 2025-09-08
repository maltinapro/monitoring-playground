package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final WebClient webClient;
    private final String applicationName;
    private final String nextServiceUrl;

    public HelloController(
            WebClient.Builder webClientBuilder,
            @Value("${spring.application.name}") String applicationName,
            @Value("${next.service.url:}") String nextServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(nextServiceUrl).build();
        this.applicationName = applicationName;
        this.nextServiceUrl = nextServiceUrl;
    }

    @GetMapping("/hello")
    public String hello() {
        logger.info("Handling request in {}", applicationName);
        String message = "Hello from " + applicationName;

        if (!nextServiceUrl.isEmpty()) {
            logger.info("Calling next service at {}", nextServiceUrl);
            try {
                // Aufruf des externen Dienstes mit WebClient
                String nextServiceResponse = webClient.get()
                        .uri("/hello")
                        .retrieve()
                        .bodyToMono(String.class)
                        .block(); // block() ist hier für synchrone Kompatibilität
                
                message += " -> " + nextServiceResponse;
            } catch (Exception e) {
                logger.error("Failed to call next service", e);
                message += " -> Error calling next service";
            }
        }
        return message;
    }
}