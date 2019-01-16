package com.cryteck.universepath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CrytekUniverseModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrytekUniverseModuleApplication.class,
				args);
	}

}
