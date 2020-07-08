package com.rebwon.gsasyncmethod.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {
	private final GithubLookupService githubLookupService;

	@Override
	public void run(String... args) throws Exception {
		long start = System.currentTimeMillis();

		CompletableFuture<User> page1 = githubLookupService.findUser("Rebwon");
		CompletableFuture<User> page2 = githubLookupService.findUser("naver");
		CompletableFuture<User> page3 = githubLookupService.findUser("Spring-Projects");

		CompletableFuture.allOf(page1,page2,page3).join();

		log.info("Elapsed time: " + (System.currentTimeMillis() - start));
		log.info("--> " + page1.get());
		log.info("--> " + page2.get());
		log.info("--> " + page3.get());
	}
}
