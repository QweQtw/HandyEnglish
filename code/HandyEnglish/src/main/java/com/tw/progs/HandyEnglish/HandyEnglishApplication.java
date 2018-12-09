package com.tw.progs.HandyEnglish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HandyEnglishApplication {

	public static final Logger log = LoggerFactory.getLogger(HandyEnglishApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(HandyEnglishApplication.class, args);
	}
}
