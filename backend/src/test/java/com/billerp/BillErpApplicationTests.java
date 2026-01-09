package com.billerp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@org.springframework.boot.autoconfigure.EnableAutoConfiguration(excludeName = {
		"org.springframework.boot.autoconfigure.data.mongodb.MongoDataAutoConfiguration",
		"org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration"
})
@org.junit.jupiter.api.Disabled("Requires MongoDB")
class BillErpApplicationTests {

	@Test
	void contextLoads() {
	}

}
