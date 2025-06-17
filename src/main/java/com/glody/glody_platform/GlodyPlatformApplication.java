package com.glody.glody_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GlodyPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlodyPlatformApplication.class, args);
	}

}
