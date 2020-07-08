package com.rebwon.gsasyncmethod.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GithubLookupService {
	private final RestTemplate restTemplate;

	public GithubLookupService(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}

	@Async
	public CompletableFuture<User> findUser(String user) throws InterruptedException {
		log.info("Looking up " + user);
		String url = String.format("https://api.github.com/users/%s", user);
		User results = restTemplate.getForObject(url, User.class);
		Thread.sleep(1000L);
		return CompletableFuture.completedFuture(results);
	}
}
