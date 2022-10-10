package com.reactivestream.reactive.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootApplication
public class ReactiveStreamApplication {


	public static void main(String[] args) {
		SpringApplication.run(ReactiveStreamApplication.class, args);
		System.out.println("hi");
	}


}
