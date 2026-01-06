package com.billerp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class BillErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillErpApplication.class, args);
	}

}
