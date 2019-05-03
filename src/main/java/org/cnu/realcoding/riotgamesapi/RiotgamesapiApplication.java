package org.cnu.realcoding.riotgamesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RiotgamesapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiotgamesapiApplication.class, args);
	}

}
