package ru.liga.prerevolutionarytindertgbotclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class PrerevolutionaryTinderTgbotClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrerevolutionaryTinderTgbotClientApplication.class, args);
	}

}
