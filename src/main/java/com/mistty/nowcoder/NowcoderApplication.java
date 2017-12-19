package com.mistty.nowcoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
//@Controller
public class NowcoderApplication {

	//@GetMapping("/hello")
	//@ResponseBody
	/*
	public String hello() {
		System.out.println("hello");
		return "Hello";
	}
	*/
	public static void main(String[] args) {
		SpringApplication.run(NowcoderApplication.class, args);
	}
}
