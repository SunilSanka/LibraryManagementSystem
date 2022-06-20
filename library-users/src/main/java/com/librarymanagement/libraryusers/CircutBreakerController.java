package com.librarymanagement.libraryusers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircutBreakerController {
private Logger logger = LoggerFactory.getLogger(CircutBreakerController.class);


	@GetMapping("/sample-api")
 	//@Retry(name="sample-api", fallbackMethod = "hardcodedResponse") // If there is a failure in this method execution, it would try 5 times
	// @CircuitBreaker(name="sample-api", fallbackMethod = "hardcodedResponse") // If there is a failure in this method execution, it would try 5 times, if @retry is enabled
	@RateLimiter(name="default")
	//@Bulkhead(name="default")
	public String sampleAPI() {

		logger.info("Sample API Received");
		/*
		ResponseEntity<String> forEntity
		= new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);

		return forEntity.getBody(); */
		return "sample-api";
	}

	public String hardcodedResponse(Exception ex) {
		return "fallback-response";
	}
}
