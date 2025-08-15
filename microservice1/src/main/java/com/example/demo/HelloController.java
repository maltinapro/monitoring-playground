package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${service.name}")
    private String serviceName;

    @Value("${next.service.url:}")
    private String nextServiceUrl;

    @GetMapping("/hello")
    public String hello() {
        logger.info("Handling request in {}", serviceName);
        String message = "Hello from " + serviceName;

        if (!nextServiceUrl.isEmpty()) {
            logger.info("Calling next service at {}", nextServiceUrl);
            try {
                RestTemplate restTemplate = new RestTemplate();
                String nextServiceResponse = restTemplate.getForObject(nextServiceUrl + "/hello", String.class);
                message += " -> " + nextServiceResponse;
            } catch (Exception e) {
                logger.error("Failed to call next service", e);
                message += " -> Error calling next service";
            }
        }
        return message;
    }
}
