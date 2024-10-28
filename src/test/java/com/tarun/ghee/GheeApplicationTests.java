package com.tarun.ghee;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
@Disabled
class GheeApplicationTests {

	@Test
	@Disabled("Disabled until 2 features implemented")
	void contextLoads() {
	}

}
