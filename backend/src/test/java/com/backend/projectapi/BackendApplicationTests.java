package com.backend.projectapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("#############");
		System.out.println(System.getenv("DB_URL"));

	}

}
