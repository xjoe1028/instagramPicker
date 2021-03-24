package com.instagramPicker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InstagramPickerApplication {
	
	private static final Logger log = LoggerFactory.getLogger(InstagramPickerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InstagramPickerApplication.class, args);
		log.info("============= SpringBoot Start Success =============");
	}

}
